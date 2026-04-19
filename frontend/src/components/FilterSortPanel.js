import React, { useState } from 'react';
import './FilterSortPanel.css';

const FilterSortPanel = ({ onFilterChange, type, userId }) => {
  const [filters, setFilters] = useState({
    category: '',
    source: '',
    startDate: '',
    endDate: '',
    minAmount: '',
    maxAmount: '',
  });
  const [sortBy, setSortBy] = useState('');
  const [showFilters, setShowFilters] = useState(false);

  const handleFilterChange = (e) => {
    const newFilters = { ...filters, [e.target.name]: e.target.value };
    setFilters(newFilters);
  };

  const handleApplyFilters = () => {
    const params = {};

    if (type === 'expense') {
      if (filters.category) params.category = filters.category;
    } else {
      if (filters.source) params.source = filters.source;
    }

    if (filters.startDate) params.startDate = filters.startDate;
    if (filters.endDate) params.endDate = filters.endDate;
    if (filters.minAmount) params.minAmount = parseInt(filters.minAmount);
    if (filters.maxAmount) params.maxAmount = parseInt(filters.maxAmount);

    onFilterChange(params);
  };

  const handleSort = (sortValue) => {
    setSortBy(sortValue);
    onFilterChange({ sortBy: sortValue });
  };

  const handleClearFilters = () => {
    setFilters({
      category: '',
      source: '',
      startDate: '',
      endDate: '',
      minAmount: '',
      maxAmount: '',
    });
    setSortBy('');
    onFilterChange({});
  };

  return (
    <div className="filter-sort-panel">
      <div className="panel-header">
        <button
          className="toggle-btn"
          onClick={() => setShowFilters(!showFilters)}
        >
          {showFilters ? 'Hide' : 'Show'} Filters & Sort
        </button>
      </div>

      {showFilters && (
        <div className="panel-content">
          <div className="filter-section">
            <h4>Filters</h4>
            <div className="filter-grid">
              {type === 'expense' ? (
                <div className="filter-group">
                  <label>Category:</label>
                  <select
                    name="category"
                    value={filters.category}
                    onChange={handleFilterChange}
                  >
                    <option value="">All Categories</option>
                    <option value="PERSONAL">Personal</option>
                    <option value="SURVIVAL_LIVELIHOOD">Survival/Livelihood</option>
                    <option value="INVESTMENT">Investment</option>
                  </select>
                </div>
              ) : (
                <div className="filter-group">
                  <label>Source:</label>
                  <select
                    name="source"
                    value={filters.source}
                    onChange={handleFilterChange}
                  >
                    <option value="">All Sources</option>
                    <option value="FROM_INVESTMENT">From Investment</option>
                    <option value="SALARY">Salary</option>
                    <option value="FROM_TRADING">From Trading</option>
                  </select>
                </div>
              )}

              <div className="filter-group">
                <label>Start Date:</label>
                <input
                  type="date"
                  name="startDate"
                  value={filters.startDate}
                  onChange={handleFilterChange}
                />
              </div>

              <div className="filter-group">
                <label>End Date:</label>
                <input
                  type="date"
                  name="endDate"
                  value={filters.endDate}
                  onChange={handleFilterChange}
                />
              </div>

              <div className="filter-group">
                <label>Min Amount:</label>
                <input
                  type="number"
                  name="minAmount"
                  value={filters.minAmount}
                  onChange={handleFilterChange}
                  min="0"
                />
              </div>

              <div className="filter-group">
                <label>Max Amount:</label>
                <input
                  type="number"
                  name="maxAmount"
                  value={filters.maxAmount}
                  onChange={handleFilterChange}
                  min="0"
                />
              </div>
            </div>

            <div className="filter-actions">
              <button onClick={handleApplyFilters} className="btn-apply">
                Apply Filters
              </button>
              <button onClick={handleClearFilters} className="btn-clear">
                Clear All
              </button>
            </div>
          </div>

          <div className="sort-section">
            <h4>Sort By</h4>
            <div className="sort-options">
              <button
                className={sortBy === 'amount' ? 'active' : ''}
                onClick={() => handleSort('amount')}
              >
                Amount
              </button>
              <button
                className={sortBy === (type === 'expense' ? 'category' : 'source') ? 'active' : ''}
                onClick={() => handleSort(type === 'expense' ? 'category' : 'source')}
              >
                {type === 'expense' ? 'Category' : 'Source'}
              </button>
              <button
                className={sortBy === 'date' ? 'active' : ''}
                onClick={() => handleSort('date')}
              >
                Date
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default FilterSortPanel;
