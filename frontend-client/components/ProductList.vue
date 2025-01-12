<template>
  <div class="product-list">
    <h2>Lista de Produtos</h2>

    <div v-if="loading">A carregar produtos...</div>
    <div v-else-if="error">Erro ao carregar produtos.</div>
    <ul v-else>
      <li v-for="product in products" :key="product.id" class="product-item">
        <h3>{{ product.name }}</h3>
        <p><strong>Marca:</strong> {{ product.brand }}</p>
        <p><strong>Categoria:</strong> {{ product.category }}</p>
        <p><strong>Preço:</strong> €{{ product.price.toFixed(2) }}</p>
        <p><strong>Quantidade Máxima por Volume:</strong> {{ product.maxQuantityPerVolume }}</p>
        <p><strong>Tipo de Embalagem:</strong> {{ product.typeOfPackage }}</p>
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRuntimeConfig } from '#app';

const products = ref([]);
const loading = ref(true);
const error = ref(false);

const config = useRuntimeConfig();
const API_URL = `${config.public.API_URL}/products`;

const fetchProducts = async () => {
  try {
    const response = await fetch(API_URL);
    if (!response.ok) throw new Error('Erro ao buscar os produtos');
    const data = await response.json();
    products.value = data;
  } catch (err) {
    console.error(err);
    error.value = true;
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  fetchProducts();
});
</script>

<style scoped>
.product-list {
  padding: 1rem;
}

.product-item {
  background-color: #f5f5f7;
  padding: 1rem;
  margin-bottom: 1rem;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.product-item h3 {
  margin-bottom: 0.5rem;
  color: #333;
}

.product-item p {
  margin: 0.2rem 0;
}
</style>
