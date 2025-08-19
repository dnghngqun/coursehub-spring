// Dark mode toggle - Enhanced version
document.addEventListener('DOMContentLoaded', function() {
    const themeToggle = document.getElementById('themeToggle');
    const THEME_KEY = 'coursehub-theme';

    // Get current theme from localStorage or default to 'light'
    function getCurrentTheme() {
        return localStorage.getItem(THEME_KEY) || 'light';
    }

    // Apply theme to document
    function applyTheme(theme) {
        document.documentElement.setAttribute('data-bs-theme', theme);
        updateToggleIcon(theme);
    }

    // Update toggle button icon
    function updateToggleIcon(theme) {
        if (themeToggle) {
            const icon = themeToggle.querySelector('i');
            if (icon) {
                icon.className = theme === 'dark' ? 'bi bi-sun' : 'bi bi-moon-stars';
            }
        }
    }

    // Toggle theme
    function toggleTheme() {
        const currentTheme = getCurrentTheme();
        const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
        localStorage.setItem(THEME_KEY, newTheme);
        applyTheme(newTheme);
    }

    // Initialize theme on page load
    applyTheme(getCurrentTheme());

    // Add click event listener
    if (themeToggle) {
        themeToggle.addEventListener('click', toggleTheme);
    }
});

// Button loading state
document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('[data-loading]').forEach(btn => {
        btn.addEventListener('click', function(e) {
            // Don't apply loading state to submit buttons in forms
            if (this.type === 'submit') return;

            this.disabled = true;
            this.dataset.oldHtml = this.innerHTML;
            this.innerHTML = '<span class="spinner-border spinner-border-sm"></span> Đang xử lý...';

            setTimeout(() => {
                this.disabled = false;
                this.innerHTML = this.dataset.oldHtml;
            }, 2000);
        });
    });
});
