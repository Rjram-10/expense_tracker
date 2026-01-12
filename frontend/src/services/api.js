import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add token to requests if available
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Auth APIs
export const signup = (data) => api.post('/auth/signup', data);
export const signin = (data) => api.post('/auth/signin', data);

// Expense APIs
export const getExpenses = (userId) => api.get(`/expense/user/${userId}`);
export const addExpense = (data) => api.post('/expense/add', data);
export const deleteExpense = (expenseId) => api.delete(`/expense/${expenseId}`);
export const filterExpenses = (params) => api.get('/expense/filter', { params });
export const sortExpenses = (userId, sortBy) => api.get('/expense/sort', { params: { userId, sortBy } });
export const getTotalExpenses = (userId) => api.get(`/expense/total/${userId}`);

// Income APIs
export const getIncomes = (userId) => api.get(`/income/user/${userId}`);
export const addIncome = (data) => api.post('/income/add', data);
export const deleteIncome = (incomeId) => api.delete(`/income/${incomeId}`);
export const filterIncomes = (params) => api.get('/income/filter', { params });
export const sortIncomes = (userId, sortBy) => api.get('/income/sort', { params: { userId, sortBy } });
export const getTotalIncome = (userId) => api.get(`/income/total/${userId}`);

// PnL API
export const getPnL = (userId) => api.get(`/pnl/${userId}`);

export default api;
