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


// En [userHome.html](http://_vscodecontentref_/1)
const params = new URLSearchParams(window.location.search);
const token = params.get('token');
if (token) {
    sessionStorage.setItem('jwt', token);
}

// SEGURIDAD POR PAGINA
const jwt = sessionStorage.getItem("jwt");
if (!jwt) {
    window.location.href = "login.html";
} else {
    // Opcional: decodificar el jwt para chequear expiración
    const payloadBase64 = jwt.split('.')[1];
    const payloadJson = atob(payloadBase64);
    const payload = JSON.parse(payloadJson);

    const exp = payload.exp * 1000; // está en segundos, convertir a ms
    const now = new Date().getTime();

    if (now >= exp) {
        sessionStorage.removeItem("jwt");
        window.location.href = "login.html";
    }
}

// NOMBRE DE USUARIO
document.addEventListener("DOMContentLoaded", async () => {
    const jwt = sessionStorage.getItem('jwt');
    if (!jwt) {
    window.location.href = "login.html";
    return;
    }
    try {
    const res = await fetch('https://all-in-one-80bf.onrender.com/auth/currentUser', {
        method: 'GET',
        headers: {
        'Authorization': `Bearer ${jwt}`
        }
    });
    if (!res.ok) throw new Error("No se pudo obtener el usuario");
    const username = await res.text();
    document.getElementById('username').textContent = username + "!";
    } catch (e) {
    document.getElementById('username').textContent = "User!";
    }
    loadLinks(); // Cargar links al iniciar
});

// LOGOUT
document.getElementById('logoutBtn').addEventListener('click', function(e) {
    e.preventDefault();
    sessionStorage.removeItem('jwt');
    window.location.href = "index.html";
});

// Modal de confirmación personalizado
let modal = document.getElementById('customConfirmModal');
let confirmBtn = null;
let cancelBtn = null;
let onConfirmDelete = null;

function showCustomConfirm(onConfirm) {
const modal = document.getElementById('customConfirmModal');
const confirmBtn = document.getElementById('confirmDeleteBtn');
const cancelBtn = document.getElementById('cancelDeleteBtn');
modal.classList.remove('hidden');
confirmBtn.onclick = () => {
    modal.classList.add('hidden');
    if (onConfirm) onConfirm();
};
cancelBtn.onclick = () => {
    modal.classList.add('hidden');
};
}

// CARGAR LINKS DEL BACKEND
async function loadLinks() {
const jwt = sessionStorage.getItem('jwt');
if (!jwt) return;
try {
    const res = await fetch('https://all-in-one-80bf.onrender.com/link', {
    headers: { 'Authorization': `Bearer ${jwt}` }
    });
    if (!res.ok) throw new Error("No se pudieron obtener los links");
    const links = await res.json();
    const container = document.getElementById('linksContainer');
    container.innerHTML = ""; // Limpiar contenido previo

    links.forEach(link => {
    const card = document.createElement('div');
    card.className = "relative bg-white/90 rounded-2xl shadow-lg p-3 sm:p-6 flex flex-col gap-2 hover:scale-[1.03] hover:shadow-xl transition-all duration-200 border border-gray-100";
    card.innerHTML = `
        <div class="absolute top-3 right-3 flex gap-2 z-10">
        <button class="bg-white/80 border border-gray-200 rounded-full text-gray-400 hover:text-blue-600 hover:bg-blue-50 text-xl font-bold shadow-sm focus:outline-none transition-all duration-200 p-2"
            title="Editar" aria-label="Editar" id="editBtn-${link.id}">
            <svg class="w-5 h-5" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" d="M15.232 5.232l3.536 3.536M9 13l6.586-6.586a2 2 0 112.828 2.828L11.828 15.828a2 2 0 01-2.828 0L9 13z"/>
            </svg>
        </button>
        <button class="bg-white/80 border border-gray-200 rounded-full text-gray-400 hover:text-red-600 hover:bg-red-50 text-xl font-bold shadow-sm focus:outline-none transition-all duration-200 p-2"
            title="Eliminar" aria-label="Eliminar" id="deleteBtn-${link.id}">
            <svg class="w-5 h-5" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" d="M6 18L18 6M6 6l12 12"/>
            </svg>
        </button>
        </div>
        <div class="flex items-center gap-3 mb-2 mt-2">
        <div class="w-10 h-10 flex items-center justify-center rounded-full bg-[#FF5A5F]/10 border-2 border-[#FF5A5F] shadow-sm">
            <svg class="w-5 h-5 text-[#FF5A5F]" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" d="M14 3h7v7m0 0L10 21l-7-7 11-11z"/>
            </svg>
        </div>
        <h3 class="flex-1 text-lg sm:text-xl font-extrabold text-gray-900 truncate">${link.title ? link.title : 'Sin título'}</h3>
        </div>
        <div class="flex flex-wrap items-center gap-2 mb-2">
        <span class="inline-block bg-[#FF5A5F]/10 text-[#FF5A5F] text-xs font-semibold px-2 py-0.5 rounded-full">${link.categoryName ? link.categoryName : 'Sin categoría'}</span>
        <span class="font-semibold text-gray-700 text-sm sm:text-base break-all">${link.description ? link.description : ''}</span>
        </div>
        ${getEmbedHTML(link.link)}
        <a href="${link.link}" target="_blank"
        class="inline-flex items-center gap-2 bg-[#FF5A5F] text-white px-4 py-2 rounded-full font-semibold text-sm shadow hover:bg-black hover:scale-105 transition-all duration-200 focus:outline-none focus:ring-2 focus:ring-[#FF5A5F] focus:ring-offset-2 mt-1 mb-1 w-full justify-center sm:w-auto">
        <svg class="w-4 h-4" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" d="M14 3h7v7m0 0L10 21l-7-7 11-11z"/>
        </svg>
        Ir al sitio
        </a>
    `;

    // Botón de editar
    const editBtn = card.querySelector(`#editBtn-${link.id}`);
    editBtn.addEventListener('click', async (e) => {
    e.stopPropagation();
    document.getElementById('editTitle').value = link.title || '';
    document.getElementById('editLink').value = link.link || '';
    document.getElementById('editDescription').value = link.description || '';
    // Cargar categorías antes de mostrar el modal
    await loadCategories();
    document.getElementById('editCategory').value = (link.category && link.category.name) ? link.category.name : (link.categoryName || '');
    document.getElementById('editModal').classList.remove('hidden');

    // Evitar múltiples listeners
    const saveBtn = document.getElementById('saveEditBtn');
    saveBtn.onclick = null;
    saveBtn.onclick = async () => {
        const jwt = sessionStorage.getItem('jwt');
        const updated = {
        id: link.id,
        title: document.getElementById('editTitle').value,
        link: document.getElementById('editLink').value,
        description: document.getElementById('editDescription').value,
        categoryName: document.getElementById('editCategory').value, // <--- CAMBIA AQUÍ
        username: link.username
        };
        const res = await fetch('https://all-in-one-80bf.onrender.com/link', {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${jwt}`
        },
        body: JSON.stringify(updated)
        });
        if (res.ok) {
        document.getElementById('editModal').classList.add('hidden');
        loadLinks();
        } else {
        alert('Error al actualizar el link');
        }
    };
    });

    // Botón de eliminar
    const deleteBtn = card.querySelector(`#deleteBtn-${link.id}`);
    deleteBtn.addEventListener('click', (e) => {
        e.stopPropagation();
        showCustomConfirm(async () => {
        try {
            const jwt = sessionStorage.getItem('jwt');
            const res = await fetch(`https://all-in-one-80bf.onrender.com/link/${link.id}`, {
            method: 'DELETE',
            headers: { 'Authorization': `Bearer ${jwt}` }
            });
            if (res.ok) {
            card.remove();
            } else {
            alert('No se pudo eliminar el link.');
            }
        } catch {
            alert('Error al eliminar el link.');
        }
        });
    });

    container.appendChild(card);
    });

} catch (e) {
    // Puedes mostrar un mensaje de error si lo deseas
}
}

// registrar correctamente
function showSuccessModal() {
const modal = document.getElementById('successModal');
const btn = document.getElementById('closeSuccessBtn');
modal.classList.remove('hidden');
btn.onclick = () => {
    modal.classList.add('hidden');
    loadLinks(); // Recargar los links después de cerrar el modal
};
}

// add link
function closeModal() {
    document.getElementById('modal').style.display = 'none';
}

async function addLink() {
    const jwt = sessionStorage.getItem('jwt');
    const title = document.getElementById('title').value;
    const link = document.getElementById('link').value;
    const description = document.getElementById('description').value;
    const category = document.getElementById('category').value;

    const body = {
    title,
    link,
    description,
    category: { name: category }
    };

    const response = await fetch('https://all-in-one-80bf.onrender.com/link', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${jwt}`
    },
    body: JSON.stringify(body)
    });

    if (response.ok) {
    closeModal();
    // Limpiar campos
    document.getElementById('title').value = '';
    document.getElementById('link').value = '';
    document.getElementById('description').value = '';
    document.getElementById('category').value = '';
    showSuccessModal();
    } else {
    alert('Error al agregar el link');
    }
}

// Cargar categorías y limpiar duplicados/vacíos
async function loadCategories() {
    const jwt = sessionStorage.getItem('jwt');
    if (!jwt) return;
    try {
    const res = await fetch('https://all-in-one-80bf.onrender.com/category/currentUser', {
        headers: { 'Authorization': `Bearer ${jwt}` }
    });
    if (!res.ok) throw new Error("No se pudieron obtener las categorías");
    let categories = await res.json();

    // Filtrar duplicados, vacíos, null y "undefined"
    categories = categories.filter(
        (cat, idx, arr) =>
        cat && cat !== "undefined" && arr.indexOf(cat) === idx
    );

    const datalist = document.getElementById('categoryList');
    datalist.innerHTML = '';
    categories.forEach(cat => {
        const option = document.createElement('option');
        option.value = cat;
        datalist.appendChild(option);
    });
    } catch (e) {
    // Puedes mostrar un mensaje de error si lo deseas
    }
}

document.getElementById('addLinkBtn').addEventListener('click', function(e) {
e.preventDefault();
document.getElementById('modal').style.display = 'flex';
loadCategories(); // Llama la función correctamente
});


// Cancelar edición
document.getElementById('cancelEditBtn').onclick = () => {
    document.getElementById('editModal').classList.add('hidden');
};

function getEmbedHTML(link) {
let ytId = null;

// Shorts: https://youtube.com/shorts/VIDEO_ID
let shortsMatch = link.match(/youtube\.com\/shorts\/([A-Za-z0-9_-]{11})/);
if (shortsMatch) {
    ytId = shortsMatch[1];
    // Mostrar aviso en vez de iframe
return `
    <div class="w-full aspect-video rounded-xl my-2 flex flex-col items-center justify-center bg-gray-100 border border-gray-200 text-center">
    <span class="text-gray-500 text-sm mb-2 w-full block">Este Short de YouTube no puede ser reproducido aquí.</span>
    <a href="https://youtube.com/shorts/${ytId}" target="_blank"
        class="inline-flex items-center gap-2 bg-[#FF5A5F] text-white px-4 py-2 rounded-full font-semibold text-sm shadow hover:bg-black transition-all">
        Ver en YouTube
    </a>
    </div>
`;
}

// youtu.be/VIDEO_ID o youtube.com/watch?v=VIDEO_ID
let ytMatch = link.match(/(?:youtu\.be\/|youtube\.com\/(?:watch\?v=|embed\/))([A-Za-z0-9_-]{11})/);
if (!ytMatch) {
    try {
    const urlObj = new URL(link, window.location.origin);
    if (urlObj.hostname.includes('youtube.com') && urlObj.searchParams.get('v')) {
        ytMatch = [null, urlObj.searchParams.get('v')];
    }
    } catch {}
}
if (ytMatch && ytMatch[1]) {
    return `<iframe class="w-full aspect-video rounded-xl my-2" src="https://www.youtube.com/embed/${ytMatch[1]}" frameborder="0" allowfullscreen></iframe>`;
}

// 4. youtube.com/embed/VIDEO_ID
if (!ytId) {
    let embedMatch = link.match(/youtube\.com\/embed\/([A-Za-z0-9_-]{11})/);
    if (embedMatch) ytId = embedMatch[1];
}

if (ytId) {
    return `<iframe class="w-full aspect-video rounded-xl my-2" src="https://www.youtube.com/embed/${ytId}" frameborder="0" allowfullscreen></iframe>`;
}


// Instagram Reel
const igMatch = link.match(/instagram\.com\/reel\/([A-Za-z0-9_-]+)/);
if (igMatch) {
    return `<iframe class="w-full aspect-[9/16] rounded-xl my-2" src="https://www.instagram.com/reel/${igMatch[1]}/embed" frameborder="0" allowfullscreen></iframe>`;
}

// TikTok
const ttMatch = link.match(/tiktok\.com\/(@[\w.-]+)\/video\/(\d+)/);
if (ttMatch) {
    return `<iframe class="w-full aspect-[9/16] rounded-xl my-2" src="https://www.tiktok.com/embed/v2/${ttMatch[2]}" frameborder="0" allowfullscreen></iframe>`;
}

return '';
}