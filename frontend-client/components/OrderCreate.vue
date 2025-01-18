<template>
    <div class="new-order">
        <h2>Criar Nova Encomenda</h2>
        <form @submit.prevent="createOrder">
            <div>
                <label for="order_id">Id da Encomenda:</label>
                <input type="text" id="order_id" v-model="newOrder.order_id" required />
            </div>
            <div>
                <label for="destination">Destino:</label>
                <input type="text" id="destination" v-model="newOrder.destination" required />
            </div>
            <h3>Produtos</h3>
            <div v-for="(product, index) in newOrder.products" :key="index" class="product-item">
                <div>
                    <label>ID do Produto: </label>
                    <input type="text" v-model="product.id" required />
                </div>
                <div>
                    <label>Nome: </label>
                    <input type="text" v-model="product.name" required />
                </div>
                <div>
                    <label>Marca: </label>
                    <input type="text" v-model="product.brand" required />
                </div>
                <div>
                    <label>Preço: </label>
                    <input type="number" v-model="product.price" required />
                </div>
                <div>
                    <label>Quantidade: </label>
                    <input type="number" v-model="product.quantityOrdered" min="1" required />
                </div>
                <div>
                    <label>Categoria: </label>
                    <input type="text" v-model="product.category" min="1" required />
                </div>
                <div>
                    <label>Maximo quantidade por volume: </label>
                    <input type="number" v-model="product.maxQuantityPerVolume" min="1" required />
                </div>
                <div>
                    <label>Tipo de package: </label>
                    <input type="text" v-model="product.typeOfPackage" min="1" required />
                </div>
        <button type="button" @click="removeProduct(index)">Remover</button>
      </div>
      <button type="button" @click="addProduct">Adicionar Produto</button>
      <button type="submit">Criar Encomenda</button>
    </form>
  </div>
</template>

<script setup lang="ts">
    import { ref } from 'vue';
import { useRuntimeConfig } from '#app';
import { useAuthStore } from '@/stores/auth';

const config = useRuntimeConfig();
const authStore = useAuthStore();
const BASE_URL = `${config.public.API_URL}/loja/encomendas`;

const newOrder = ref({
  order_id: 0,
  client_username: authStore.username,
  destination: '',
  products: [],
});


/**
 * Add a new product to the order
 */
function addProduct() {
  newOrder.value.products.push({
    id: '',
    name: '',
    brand:'',
    price: 0.0,
    quantityOrdered: 1,
    category: '',
    maxQuantityPerVolume: 0,
    typeOfPackage: '',
    order_id: newOrder.order_id,
  });
}

/**
 * Remove a product from the order by index
 * @param {number} index - Index of the product to remove
 */
function removeProduct(index) {
  newOrder.value.products.splice(index, 1);
}

/**
 * Create a new order by sending data to the server
 */
async function createOrder() {
  try {
    // Validate that there is at least one product
    if (newOrder.value.products.length === 0) {
      alert('Adicione pelo menos um produto.');
      return;
    }

    const response = await fetch(`${BASE_URL}/`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${authStore.token}`,
      },
      body: JSON.stringify(newOrder.value),
    });

    if (!response.ok) {
      throw new Error('Erro ao criar encomenda.');
    }

    alert('Encomenda criada com sucesso!');
    // Reset the form
    newOrder.value = {
      clientUsername: authStore.username,
      destination: '',
      products: [],
    };
    // Emit event to notify the parent component
    alert("Order created")
  } catch (err) {
    console.error(err);
    alert('Erro ao criar a encomenda.');
  }
}
</script>

<style scoped>
.new-order {
    padding: 1.5rem;
    margin: 5px auto; /* Center horizontally */
    background-color: #f9f9f9;
    border-radius: 8px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    max-width: 400px; /* Optional: Set a max-width */
}

.new-order-container {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh; /* Take the full viewport height */
    background-color: #f5f5f7; /* Optional background color */
}

.new-order h2 {
    margin-bottom: 1rem;
}

.new-order form > div {
    margin-bottom: 1rem;
}

.product-item {
    display: flexbox;
    gap: 1rem;
    align-items: center;
    margin-bottom: 0.5rem;
}
</style>
