document.addEventListener('DOMContentLoaded', () => {
    // CRITICAL: Make sure the key is exactly 'jwtToken'
    const token = localStorage.getItem('jwtToken');
    
    const anonymousButtons = document.getElementById('anonymous-buttons');
    const userButtons = document.getElementById('user-buttons');
    const adminButtons = document.getElementById('admin-buttons');

    if (token) {
        try {
            // If a token exists, the user is logged in
            const payload = JSON.parse(atob(token.split('.')[1]));
            const roles = payload.roles || [];

            // Hide anonymous buttons
            if (anonymousButtons) anonymousButtons.style.display = 'none';

            // Show buttons based on role
            if (roles.includes('ROLE_ADMIN')) {
                if (adminButtons) adminButtons.style.display = 'flex'; // Use 'flex' for Bulma buttons
                if (userButtons) userButtons.style.display = 'none';
            } else if (roles.includes('ROLE_USER')) {
                if (userButtons) userButtons.style.display = 'flex'; // Use 'flex' for Bulma buttons
                if (adminButtons) adminButtons.style.display = 'none';
            }
        } catch (e) {
            // This will catch errors if the token is malformed
            console.error("Failed to parse JWT:", e);
            localStorage.removeItem('jwtToken'); // Clear the bad token
        }
    } else {
        // No token, ensure the default anonymous view is shown
        if (anonymousButtons) anonymousButtons.style.display = 'flex';
        if (userButtons) userButtons.style.display = 'none';
        if (adminButtons) adminButtons.style.display = 'none';
    }

    // --- Logout Logic ---
    const handleLogout = () => {
        localStorage.removeItem('jwtToken');
        window.location.href = '/login'; // Redirect to login page after logout
    };

    const logoutBtnUser = document.getElementById('logout-button-user');
    if (logoutBtnUser) {
        logoutBtnUser.addEventListener('click', handleLogout);
    }

    const logoutBtnAdmin = document.getElementById('logout-button-admin');
    if (logoutBtnAdmin) {
        logoutBtnAdmin.addEventListener('click', handleLogout);
    }
});