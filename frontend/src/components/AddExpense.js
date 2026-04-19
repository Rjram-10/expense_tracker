import React, { useState } from 'react';
import { addExpense } from '../services/api';
import './AddExpense.css';

const AddExpense = ({ onAdd, userId }) => {
  const [formData, setFormData] = useState({
    expenseDescription: '',
    category: 'PERSONAL',
    amount: '',
    dateOfExpense: new Date().toISOString().split('T')[0],
  });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');
    try {
      await addExpense({
        ...formData,
        userId: parseInt(userId),
        amount: parseInt(formData.amount),
        departmentId: 1,
        status: 'PENDING',
      });
      setSuccess('Expense added successfully!');
      setFormData({
        expenseDescription: '',
        category: 'PERSONAL',
        amount: '',
        dateOfExpense: new Date().toISOString().split('T')[0],
      });
      onAdd();
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to add expense');
    }
  };

  return (
    <div className="add-form-container">
      <h3>Add Expense</h3>
      {error && <div className="error-message">{error}</div>}
      {success && <div className="success-message">{success}</div>}
      <form onSubmit={handleSubmit} className="add-form">
        <div className="form-row">
          <div className="form-group">
            <label>Description:</label>
            <input
              type="text"
              name="expenseDescription"
              value={formData.expenseDescription}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label>Category:</label>
            <select
              name="category"
              value={formData.category}
              onChange={handleChange}
              required
            >
              <option value="PERSONAL">Personal</option>
              <option value="SURVIVAL_LIVELIHOOD">Survival/Livelihood</option>
              <option value="INVESTMENT">Investment</option>
            </select>
          </div>
        </div>
        <div className="form-row">
          <div className="form-group">
            <label>Amount:</label>
            <input
              type="number"
              name="amount"
              value={formData.amount}
              onChange={handleChange}
              required
              min="0"
            />
          </div>
          <div className="form-group">
            <label>Date:</label>
            <input
              type="date"
              name="dateOfExpense"
              value={formData.dateOfExpense}
              onChange={handleChange}
              required
            />
          </div>
        </div>
        <button type="submit" className="btn-primary">Add Expense</button>
      </form>
    </div>
  );
};

export default AddExpense;
