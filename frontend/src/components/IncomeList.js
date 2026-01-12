import React from 'react';
import { deleteIncome } from '../services/api';
import './ExpenseList.css';

const IncomeList = ({ incomes, onRefresh }) => {
  const handleDelete = async (incomeId) => {
    if (window.confirm('Are you sure you want to delete this income?')) {
      try {
        await deleteIncome(incomeId);
        onRefresh();
      } catch (error) {
        alert('Failed to delete income');
      }
    }
  };

  const formatSource = (source) => {
    return source.replace('_', ' ').replace(/\b\w/g, (l) => l.toUpperCase());
  };

  if (!incomes || incomes.length === 0) {
    return <div className="empty-message">No income found</div>;
  }

  return (
    <div className="list-container">
      <table className="expense-table">
        <thead>
          <tr>
            <th>Description</th>
            <th>Source</th>
            <th>Amount</th>
            <th>Date</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {incomes.map((income) => (
            <tr key={income.incomeId}>
              <td>{income.incomeDescription}</td>
              <td>{formatSource(income.source)}</td>
              <td>₹{income.amount}</td>
              <td>{new Date(income.dateOfIncome).toLocaleDateString()}</td>
              <td>
                <button
                  className="btn-delete"
                  onClick={() => handleDelete(income.incomeId)}
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default IncomeList;
