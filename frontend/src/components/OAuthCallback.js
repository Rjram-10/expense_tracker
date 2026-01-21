import React, { useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';

const OAuthCallback = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();

  useEffect(() => {
    const token = searchParams.get('token');
    const userId = searchParams.get('userId');
    const error = searchParams.get('error');

    if (error) {
      navigate('/login?error=oauth_failed');
      return;
    }

    if (token && userId) {
      localStorage.setItem('token', token);
      localStorage.setItem('userId', userId);
      navigate('/dashboard');
    } else {
      // Try to fetch from backend if token not in URL
      fetchOAuthUser();
    }
  }, [searchParams, navigate]);

  const fetchOAuthUser = async () => {
    try {
      const response = await fetch('http://192.168.49.2:30010/auth/oauth-user', {
        credentials: 'include',
        headers: {
          'Content-Type': 'application/json',
        },
      });
      
      if (response.ok) {
        const data = await response.json();
        localStorage.setItem('token', data.jwt);
        localStorage.setItem('userId', data.userId);
        navigate('/dashboard');
      } else {
        navigate('/login?error=oauth_failed');
      }
    } catch (err) {
      console.error('OAuth callback error:', err);
      navigate('/login?error=oauth_failed');
    }
  };

  return (
    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
      <div>Completing login...</div>
    </div>
  );
};

export default OAuthCallback;
