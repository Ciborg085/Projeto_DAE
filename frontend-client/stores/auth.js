import { defineStore } from 'pinia';

export const useAuthStore = defineStore('auth', {
    state: () => ({
        user: null,
        token: null,
        loading: false,
        errorMessage: '',
    }),

    actions: {
        async login(username, password) {
            this.loading = true;
            this.errorMessage = '';

            try {
                const response = await fetch('http://localhost:8080/academics/api/auth/login', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ username, password }),
                });

                if (response.ok) {
                    const result = await response.text();
                    const token = result.replace('Token: Bearer ', '').trim();

                    this.token = token;
                    localStorage.setItem('token', token);

                } else {
                    this.errorMessage = 'Credenciais inválidas!';
                }
            } catch (error) {
                this.errorMessage = 'Erro ao conectar com o servidor.';
                console.error('Erro ao autenticar:', error);
            } finally {
                this.loading = false;
            }
        },

        logout() {
            this.user = null;
            this.token = null;
            localStorage.removeItem('token');
        }
    }
});
