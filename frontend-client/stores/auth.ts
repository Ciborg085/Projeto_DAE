import { defineStore } from 'pinia';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

export const useAuthStore = defineStore('auth', () => {

    const token = ref<styring | null>(null);
    const error = ref<string | null>(null);
    const loading = ref(false);
    const router = useRouter();
    const username = ref<string | null>(null);
    const email = ref<string | null>(null);
    const role = ref<string | null>(null);

    const login = async (inputUsername: string, inputPassword: string) => {
        loading.value= true;
        error.value = null;

        try {
            const response = await fetch('http://localhost:8080/academics/api/auth/login', {
                method: 'POST',
                headers: { 
                    'Content-Type': 'application/json' 
                },
                body: JSON.stringify({ username:inputUsername, password:inputPassword }),
            });

            if (!response.ok) {
                const errorMessage = await response.text();
                throw new Error(errorMessage || 'Failed to authenticate');
            }

            const data = await response.text();
            token.value = data;
            username.value = inputUsername;
            localStorage.setItem('token', data);
            localStorage.setItem('username', inputUsername);

            await fetchProfile();
            router.push({path: '/'});
        } catch (error) {
            error.value = 'Erro ao conectar com o servidor.';
            console.error('Erro ao autenticar:', error);
        } finally {
            loading.value = false;
        }
    };

    const fetchProfile = async () => {
        try {
            const response = await fetch('http://localhost:8080/academics/api/auth/profile', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token.value}`, // Include token in Authorization header
                },
            });

            if (!response.ok) {
                throw new Error('Failed to fetch profile information.');
            }

            const profileData = await response.json();
            username.value = profileData.username;
            email.value = profileData.email;
            role.value = profileData.role;
            localStorage.setItem('email', profileData.email);
            localStorage.setItem('username', profileData.username);
            localStorage.setItem('role', profileData.role);
        } catch (err: any) {
            error.value = err.message || 'Failed to fetch profile information.';
        }
    };


    const logout = () => {
        token.value = null;
        username.value = null;
        email.value = null;
        localStorage.removeItem('token');
        localStorage.removeItem('username');
        localStorage.removeItem('email');
        router.push({path: '/login'});
    }

    const isAuthenticated = () => !!token.value || !!localStorage.getItem('token');

    const initAuth = async () => {
        token.value = localStorage.getItem('token');
        username.value = localStorage.getItem('username');
        email.value = localStorage.getItem('email');
        role.value = localStorage.getItem('role');

        if (token.value && !email.value) {
            await fetchProfile();
        }
    };

    return {
        token,
        username,
        email,
        role,
        error,
        loading,
        login,
        logout,
        isAuthenticated,
        initAuth,
        fetchProfile,
    }
});
