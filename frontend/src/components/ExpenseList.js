import React from 'react';
import { deleteExpense } from '../services/api';
import './ExpenseList.css';

const ExpenseList = ({ expenses, onRefresh }) => {
  const handleDelete = async (expenseId) => {
    if (window.confirm('Are you sure you want to delete this expense?')) {
      try {
        await deleteExpense(expenseId);
        onRefresh();
      } catch (error) {
        alert('Failed to delete expense');
      }
    }
  };

  const formatCategory = (category) => {
    return category.replace('_', '/').replace(/\b\w/g, (l) => l.toUpperCase());
  };

  if (!expenses || expenses.length === 0) {
    return <div className="empty-message">No expenses found</div>;
  }

  return (
    <div className="list-container">
      <table className="expense-table">
        <thead>
          <tr>
            <th>Description</th>
            <th>Category</th>
            <th>Amount</th>
            <th>Date</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {expenses.map((expense) => (
            <tr key={expense.expenseId}>
              <td>{expense.expenseDescription}</td>
              <td>{formatCategory(expense.category)}</td>
              <td>₹{expense.amount}</td>
              <td>{new Date(expense.dateOfExpense).toLocaleDateString()}</td>
              <td>
                <button
                  className="btn-delete"
                  onClick={() => handleDelete(expense.expenseId)}
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

export default ExpenseList;
