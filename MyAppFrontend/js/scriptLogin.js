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

  
const loginForm = document.getElementById('loginForm');
const status = document.getElementById('loginStatus');

loginForm.addEventListener('submit', async (e) => {
e.preventDefault();

const username = document.getElementById('username').value;
const password = document.getElementById('password').value;

try {
    const res = await fetch('http://localhost:8080/auth/login', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
    },
    body: JSON.stringify({ username, password }),
    });

    if (!res.ok) {
    throw new Error('Invalid credentials');
    }

    const data = await res.json();
    // Guarda el token JWT en memoria (sessionStorage)
    if (data.token) {
    sessionStorage.setItem('jwt', data.token);
    status.textContent = 'Login successful';
    status.classList.remove('text-red-500');
    status.classList.add('text-green-600');
    // Redirige pasando el token por query string
    window.location.href = "userHome.html";
    } else {
    throw new Error('No token received');
    }
} catch (err) {
    status.textContent = err.message;
    status.classList.remove('text-green-600');
    status.classList.add('text-red-500');
}
});