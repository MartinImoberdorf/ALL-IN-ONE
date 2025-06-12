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