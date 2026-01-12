import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { getExpenses, getIncomes, getPnL, filterExpenses, filterIncomes, sortExpenses, sortIncomes } from '../services/api';
import ExpenseList from './ExpenseList';
import IncomeList from './IncomeList';
import AddExpense from './AddExpense';
import AddIncome from './AddIncome';
import PnLDisplay from './PnLDisplay';
import FilterSortPanel from './FilterSortPanel';
import './Dashboard.css';

const Dashboard = () => {
  const [activeTab, setActiveTab] = useState('overview');
  const [expenses, setExpenses] = useState([]);
  const [incomes, setIncomes] = useState([]);
  const [pnl, setPnl] = useState(null);
  const [loading, setLoading] = useState(false);
  const [expenseFilters, setExpenseFilters] = useState({});
  const [incomeFilters, setIncomeFilters] = useState({});
  const navigate = useNavigate();

  const userId = localStorage.getItem('userId') || 1; // Default to 1 for testing

  useEffect(() => {
    if (!localStorage.getItem('token')) {
      navigate('/login');
      return;
    }
    fetchAllData();
  }, []);

  const fetchAllData = async () => {
    setLoading(true);
    try {
      const [expensesRes, incomesRes, pnlRes] = await Promise.all([
        getExpenses(userId),
        getIncomes(userId),
        getPnL(userId),
      ]);
      setExpenses(expensesRes.data);
      setIncomes(incomesRes.data);
      setPnl(pnlRes.data);
    } catch (error) {
      console.error('Error fetching data:', error);
    } finally {
      setLoading(false);
    }
  };

  const fetchFilteredExpenses = async (filters) => {
    setLoading(true);
    try {
      if (Object.keys(filters).length > 0 && filters.sortBy) {
        const res = await sortExpenses(userId, filters.sortBy);
        setExpenses(res.data);
      } else if (Object.keys(filters).length > 0) {
        const params = { ...filters, userId: parseInt(userId) };
        const res = await filterExpenses(params);
        setExpenses(res.data);
      } else {
        const res = await getExpenses(userId);
        setExpenses(res.data);
      }
    } catch (error) {
      console.error('Error fetching filtered expenses:', error);
    } finally {
      setLoading(false);
    }
  };

  const fetchFilteredIncomes = async (filters) => {
    setLoading(true);
    try {
      if (Object.keys(filters).length > 0 && filters.sortBy) {
        const res = await sortIncomes(userId, filters.sortBy);
        setIncomes(res.data);
      } else if (Object.keys(filters).length > 0) {
        const params = { ...filters, userId: parseInt(userId) };
        const res = await filterIncomes(params);
        setIncomes(res.data);
      } else {
        const res = await getIncomes(userId);
        setIncomes(res.data);
      }
    } catch (error) {
      console.error('Error fetching filtered incomes:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
    navigate('/login');
  };

  const handleExpenseFilterChange = (newFilters) => {
    setExpenseFilters(newFilters);
    fetchFilteredExpenses(newFilters);
  };

  const handleIncomeFilterChange = (newFilters) => {
    setIncomeFilters(newFilters);
    fetchFilteredIncomes(newFilters);
  };

  const handleRefresh = () => {
    setExpenseFilters({});
    setIncomeFilters({});
    fetchAllData();
  };

  return (
    <div className="dashboard-container">
      <header className="dashboard-header">
        <h1>Expense Tracker</h1>
        <button onClick={handleLogout} className="btn-logout">Logout</button>
      </header>

      <div className="dashboard-content">
        <nav className="dashboard-nav">
          <button
            className={activeTab === 'overview' ? 'active' : ''}
            onClick={() => setActiveTab('overview')}
          >
            Overview
          </button>
          <button
            className={activeTab === 'expenses' ? 'active' : ''}
            onClick={() => setActiveTab('expenses')}
          >
            Expenses
          </button>
          <button
            className={activeTab === 'income' ? 'active' : ''}
            onClick={() => setActiveTab('income')}
          >
            Income
          </button>
        </nav>

        {activeTab === 'overview' && (
          <div className="overview-tab">
            <PnLDisplay pnl={pnl} loading={loading} />
            <div className="lists-container">
              <div className="list-section">
                <h2>Recent Expenses</h2>
                <ExpenseList expenses={expenses.slice(0, 5)} onRefresh={handleRefresh} />
              </div>
              <div className="list-section">
                <h2>Recent Income</h2>
                <IncomeList incomes={incomes.slice(0, 5)} onRefresh={handleRefresh} />
              </div>
            </div>
          </div>
        )}

        {activeTab === 'expenses' && (
          <div className="expenses-tab">
            <div className="tab-header">
              <h2>Expenses</h2>
              <button onClick={handleRefresh} className="btn-refresh">Refresh</button>
            </div>
            <AddExpense onAdd={handleRefresh} userId={userId} />
            <FilterSortPanel
              onFilterChange={handleExpenseFilterChange}
              type="expense"
              userId={userId}
            />
            <ExpenseList expenses={expenses} onRefresh={handleRefresh} />
          </div>
        )}

        {activeTab === 'income' && (
          <div className="income-tab">
            <div className="tab-header">
              <h2>Income</h2>
              <button onClick={handleRefresh} className="btn-refresh">Refresh</button>
            </div>
            <AddIncome onAdd={handleRefresh} userId={userId} />
            <FilterSortPanel
              onFilterChange={handleIncomeFilterChange}
              type="income"
              userId={userId}
            />
            <IncomeList incomes={incomes} onRefresh={handleRefresh} />
          </div>
        )}
      </div>
    </div>
  );
};

export default Dashboard;
