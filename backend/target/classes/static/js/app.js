/**
 * app.js — Shared API layer and UI utilities
 * Used by all pages in the Student Management System.
 */

const BASE = '';  // Same-origin (Spring Boot serves frontend + backend)

/**
 * Generic API helper — wraps fetch with JSON parsing and error handling.
 */
async function api(path, method = 'GET', body = null) {
  const opts = {
    method,
    headers: { 'Content-Type': 'application/json' }
  };
  if (body) opts.body = JSON.stringify(body);

  const res = await fetch(BASE + path, opts);

  if (res.status === 204) return null;  // No content (DELETE)

  const data = await res.json().catch(() => ({}));

  if (!res.ok) {
    throw new Error(data.message || `HTTP ${res.status}`);
  }
  return data;
}

/**
 * Check if user is logged in
 */
function isLoggedIn() {
  return localStorage.getItem('user') !== null;
}

/**
 * Logout
 */
function logout() {
  localStorage.removeItem('user');
  window.location.href = 'login.html';
}

/**
 * Toast notification
 * @param {string} message
 * @param {'success'|'error'} type
 */
function toast(message, type = 'success') {
  const container = document.getElementById('toasts');
  if (!container) return;

  const el = document.createElement('div');
  el.className = `toast ${type}`;
  el.innerHTML = `<span>${type === 'success' ? '✅' : '❌'}</span><span>${message}</span>`;
  container.appendChild(el);

  setTimeout(() => el.remove(), 3500);
}

/**
 * Open a modal overlay by ID.
 */
function openModal(id) {
  const el = document.getElementById(id);
  if (el) el.classList.add('open');
}

/**
 * Close a modal overlay by ID.
 */
function closeModal(id) {
  const el = document.getElementById(id);
  if (el) el.classList.remove('open');
}

// Close modals when clicking the overlay background
document.addEventListener('DOMContentLoaded', () => {
  // Check login for protected pages
  const protectedPages = ['index.html', 'students.html', 'courses.html', 'enrollment.html'];
  const currentPage = window.location.pathname.split('/').pop() || 'index.html';
  if (protectedPages.includes(currentPage) && !isLoggedIn()) {
    window.location.href = 'login.html';
  }

  // Add logout to sidebar if logged in
  if (isLoggedIn()) {
    const sidebar = document.querySelector('.sidebar');
    if (sidebar) {
      const logoutBtn = document.createElement('a');
      logoutBtn.className = 'nav-link';
      logoutBtn.innerHTML = '<span class="icon">🚪</span> Logout';
      logoutBtn.onclick = logout;
      sidebar.appendChild(logoutBtn);
    }
  }

  document.querySelectorAll('.modal-overlay').forEach(overlay => {
    overlay.addEventListener('click', e => {
      if (e.target === overlay) overlay.classList.remove('open');
    });
  });
});
