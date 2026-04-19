import React from 'react';
import './PnLDisplay.css';

const PnLDisplay = ({ pnl, loading }) => {
  if (loading) {
    return <div className="pnl-container">Loading...</div>;
  }

  if (!pnl) {
    return null;
  }

  const isPositive = pnl.pnl >= 0;

  return (
    <div className="pnl-container">
      <div className="pnl-card">
        <h2>Profit & Loss Summary</h2>
        <div className="pnl-stats">
          <div className="stat-item">
            <span className="stat-label">Total Income</span>
            <span className="stat-value income">₹{pnl.totalIncome || 0}</span>
          </div>
          <div className="stat-item">
            <span className="stat-label">Total Expenses</span>
            <span className="stat-value expense">₹{pnl.totalExpenses || 0}</span>
          </div>
          <div className="stat-item highlight">
            <span className="stat-label">Net P&L</span>
            <span className={`stat-value ${isPositive ? 'profit' : 'loss'}`}>
              ₹{pnl.pnl || 0}
            </span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default PnLDisplay;
