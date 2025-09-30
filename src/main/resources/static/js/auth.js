document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('login-form');
    if (!loginForm) return;

    const errorMessageDiv = document.getElementById('error-message');

    loginForm.addEventListener('submit', async (event) => {
        event.preventDefault();

        errorMessageDiv.style.display = 'none';
        errorMessageDiv.textContent = '';

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        try {
            const response = await fetch('/api/auth/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password }),
            });

            if (!response.ok) {
                errorMessageDiv.textContent = 'Invalid username or password.';
                errorMessageDiv.style.display = 'block';
                return;
            }

            const data = await response.json();
            
            // CRITICAL: Make sure the key is exactly 'jwtToken'
            localStorage.setItem('jwtToken', data.token);
            
            // Redirect to a user-specific page after successful login
            window.location.href = '/home'; // Or '/user/dashboard' etc.

        } catch (error) {
            console.error('Login error:', error);
            errorMessageDiv.textContent = 'An error occurred. Please try again.';
            errorMessageDiv.style.display = 'block';
        }
    });
});