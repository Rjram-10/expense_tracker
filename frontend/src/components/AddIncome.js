import React, { useState } from 'react';
import { addIncome } from '../services/api';
import './AddExpense.css';

const AddIncome = ({ onAdd, userId }) => {
  const [formData, setFormData] = useState({
    incomeDescription: '',
    source: 'SALARY',
    amount: '',
    dateOfIncome: new Date().toISOString().split('T')[0],
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
      await addIncome({
        ...formData,
        userId: parseInt(userId),
        amount: parseInt(formData.amount),
      });
      setSuccess('Income added successfully!');
      setFormData({
        incomeDescription: '',
        source: 'SALARY',
        amount: '',
        dateOfIncome: new Date().toISOString().split('T')[0],
      });
      onAdd();
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to add income');
    }
  };

  return (
    <div className="add-form-container">
      <h3>Add Income</h3>
      {error && <div className="error-message">{error}</div>}
      {success && <div className="success-message">{success}</div>}
      <form onSubmit={handleSubmit} className="add-form">
        <div className="form-row">
          <div className="form-group">
            <label>Description:</label>
            <input
              type="text"
              name="incomeDescription"
              value={formData.incomeDescription}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label>Source:</label>
            <select
              name="source"
              value={formData.source}
              onChange={handleChange}
              required
            >
              <option value="FROM_INVESTMENT">From Investment</option>
              <option value="SALARY">Salary</option>
              <option value="FROM_TRADING">From Trading</option>
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
              name="dateOfIncome"
              value={formData.dateOfIncome}
              onChange={handleChange}
              required
            />
          </div>
        </div>
        <button type="submit" className="btn-primary">Add Income</button>
      </form>
    </div>
  );
};

export default AddIncome;
