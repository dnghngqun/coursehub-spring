// Dark mode toggle - Enhanced version with debug
document.addEventListener('DOMContentLoaded', function() {
    console.log('🎯 CourseHub: DOM loaded, initializing theme toggle...');

    const themeToggle = document.getElementById('themeToggle');
    const THEME_KEY = 'coursehub-theme';

    if (!themeToggle) {
        console.error('❌ Theme toggle button not found!');
        return;
    }

    console.log('✅ Theme toggle button found');

    // Get current theme from localStorage or default to 'light'
    function getCurrentTheme() {
        return localStorage.getItem(THEME_KEY) || 'light';
    }

    // Apply theme to document
    function applyTheme(theme) {
        console.log(`🎨 Applying theme: ${theme}`);
        document.documentElement.setAttribute('data-bs-theme', theme);
        document.body.setAttribute('data-bs-theme', theme);
        updateToggleIcon(theme);
    }

    // Update toggle button icon
    function updateToggleIcon(theme) {
        const icon = themeToggle.querySelector('i');
        if (icon) {
            icon.className = theme === 'dark' ? 'bi bi-sun' : 'bi bi-moon-stars';
            console.log(`🔄 Icon updated to: ${icon.className}`);
        }
    }

    // Toggle theme
    function toggleTheme() {
        const currentTheme = getCurrentTheme();
        const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
        console.log(`🔀 Toggling theme: ${currentTheme} → ${newTheme}`);
        localStorage.setItem(THEME_KEY, newTheme);
        applyTheme(newTheme);
    }

    // Initialize theme on page load
    const initialTheme = getCurrentTheme();
    console.log(`🚀 Initial theme: ${initialTheme}`);
    applyTheme(initialTheme);

    // Add click event listener
    themeToggle.addEventListener('click', function(e) {
        e.preventDefault();
        console.log('🖱️ Theme toggle clicked!');
        toggleTheme();
    });

    console.log('✅ Theme toggle initialized successfully');
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
