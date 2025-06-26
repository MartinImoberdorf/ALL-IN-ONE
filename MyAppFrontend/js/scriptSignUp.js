tailwind.config = {
    theme: {
    extend: {
        fontFamily: { sans: ['Inter', 'sans-serif'] },
        keyframes: {
        'fade-in-up': {
            '0%': { opacity: '0', transform: 'translateY(40px)' },
            '100%': { opacity: '1', transform: 'translateY(0)' },
        },
        'pulse-border': {
            '0%, 100%': { boxShadow: '0 0 0 0 rgba(255,90,95,0.7)' },
            '50%': { boxShadow: '0 0 0 12px rgba(255,90,95,0)' },
        }
        },
        animation: {
        'fade-in-up': 'fade-in-up 0.8s cubic-bezier(.4,0,.2,1) both',
        'pulse-border': 'pulse-border 2s infinite',
        'spin-slow': 'spin 8s linear infinite',
        }
    },
    },
};

  const signupForm = document.getElementById('signupForm');
    const status = document.getElementById('signupStatus');

    function validatePassword(password) {
      // Min 8 chars, 1 uppercase, 1 lowercase, 1 number
      return /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d@$!%*?&]{8,}$/.test(password);
    }

signupForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    status.textContent = '';
    status.classList.remove('text-green-600', 'text-red-500');

    const email = document.getElementById('email').value.trim();
    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value;
    const repeatPassword = document.getElementById('repeatPassword').value;

    if (password !== repeatPassword) {
    status.textContent = "Passwords do not match.";
    status.classList.add('text-red-500');
    return;
    }
    if (!validatePassword(password)) {
    status.textContent = "Password must be at least 8 characters, include uppercase, lowercase, and a number.";
    status.classList.add('text-red-500');
    return;
    }

    try {
    const res = await fetch('https://all-in-one-80bf.onrender.com/auth/register/user', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, username, password })
    });

    if (res.ok) {
        status.textContent = "Registration successful! You can now log in.";
        status.classList.add('text-green-600');
        setTimeout(() => window.location.href = "login.html", 1500);
    } else {
        const error = await res.text();
        status.textContent = error || "Registration failed.";
        status.classList.add('text-red-500');
    }
    } catch (err) {
    status.textContent = "Network error. Please try again.";
    status.classList.add('text-red-500');
    }
});