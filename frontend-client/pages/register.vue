<template>
  <div class="register-container">
    <RegisterForm @register-success="onRegisterSuccess" />
    <NuxtLink to="/login" class="login-link">Já tem uma conta? Entrar</NuxtLink>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import RegisterForm from '~/components/RegisterForm.vue';

const router = useRouter();

const onRegisterSuccess = () => {
  alert('Conta criada com sucesso!');
  router.push('/login');  // Redireciona para o login
};

// Define o layout correto
definePageMeta({
  layout: 'auth'
});
</script>

<style scoped>
.register-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f0f2f5;
}

.login-link {
  margin-top: 1rem;
  color: #4caf50;
  text-decoration: none;
  font-weight: bold;
}

.login-link:hover {
  text-decoration: underline;
}
</style>
