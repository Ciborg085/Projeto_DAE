<template>
    <div class="product-list">
        <h2>Lista de Produtos</h2>

        <div v-if="loading">A carregar produtos...</div>
        <div v-else-if="error">Erro ao carregar produtos.</div>



        <table v-else>
            <thead>
                <tr>
                    <th @click.right="clearSort()" @click="sortTable('name')">Nome <span>{{ sortIcon('name') }}</span></th>
                    <th @click.right="clearSort()" @click="sortTable('brand')">Marca <span>{{ sortIcon('brand') }}</span></th>
                    <th @click.right="clearSort()" @click="sortTable('category')">Categoria <span>{{ sortIcon('category') }}</span></th>
                    <th @click.right="clearSort()" @click="sortTable('quantityOrdered')">Quantidade Encomendada<span>{{ sortIcon('quantityOrdered') }}</span></th>
                    <th @click.right="clearSort()" @click="sortTable('maxQuantityPerVolume')">Quantidade Máxima por Volume<span>{{ sortIcon('maxQuantityPerVolume') }}</span></th>
                    <th @click.right="clearSort()" @click="sortTable('price')">Preço (€) <span>{{ sortIcon('price') }}</span></th>
                    <th @click.right="clearSort()" @click="sortTable('typeOfPackage')">Tipo de Embalagem <span>{{ sortIcon('typeOfPackage') }}</span></th>
                    <th @click.right="clearSort()" @click="sortTable('order_id')">Id da Encomenda <span>{{ sortIcon('order_id') }}</span></th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="product in sortedProducts" :key="product.id" class="product-item">
                    <td>{{ product.name }}</td>
                    <td>{{ product.brand }}</td>
                    <td>{{ product.category }}</td>
                    <td>{{ product.quantityOrdered }}</td>
                    <td>{{ product.maxQuantityPerVolume }}</td>
                    <td>€{{ product.price.toFixed(2) }}</td>
                    <td>{{ product.typeOfPackage }}</td>
                    <td>{{ product.order_id }}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script setup lang="ts">
    import { ref, onMounted } from 'vue';
import { useRuntimeConfig } from '#app';
import { useAuthStore } from '@/stores/auth'

const products = ref([]);
const loading = ref(true);
const error = ref(false);
const sortColumn = ref('');
const sortOrder = ref('asc'); // asc or desc

const config = useRuntimeConfig();
const API_URL = `${config.public.API_URL}/products`;
const authStore = useAuthStore();

const fetchProducts = async () => {
  console.log(authStore.token);
  if (!authStore.token) {
    loading.value = false;
    error.value = true;
    throw new Error('Precisa de inicar a sessão');
  }
  try {
    const response = await fetch(API_URL, {
    method:'GET',
    headers: {
        Authorization: `Bearer  ${authStore.token}` 
    },
    });
    if (!response.ok) throw new Error('Erro ao buscar os produtos');
    const data = await response.json();
    products.value = data;
  } catch (err) {
    error.value = true;
  } finally {
    loading.value = false;
  }
};

const sortTable = (column) => {
  if (sortColumn.value === column) {
    sortOrder.value = sortOrder.value === 'asc' ? 'desc' : 'asc';
  } else {
    sortColumn.value = column;
    sortOrder.value = 'asc';
  }
};

const clearSort = () => {
    if (sortColumn.value != '') {
        sortColumn.value = '';
        sortOrder.value = 'asc';;
    }
}

const sortedProducts = computed(() => {
  const sorted = [...products.value];
  if (!sortColumn.value) return sorted;

  sorted.sort((a, b) => {
    const valueA = a[sortColumn.value];
    const valueB = b[sortColumn.value];

    if (typeof valueA === 'string' && typeof valueB === 'string') {
      return sortOrder.value === 'asc'
        ? valueA.localeCompare(valueB)
        : valueB.localeCompare(valueA);
    } else {
      return sortOrder.value === 'asc' ? valueA - valueB : valueB - valueA;
    }
  });

  return sorted;
});

const sortIcon = (column) => {
  if (sortColumn.value !== column) return '';
  return sortOrder.value === 'asc' ? '⬆️' : '⬇️';
};

onMounted(() => {
  authStore.initAuth();
  fetchProducts();
});
</script>

<style scoped>
.product-list {
    padding: 2rem;
    background-color: #ffffff;
    border-radius: 12px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    max-width: 800px;
    margin: 0 auto; /* Centraliza horizontalmente */
    text-align: center; /* Centraliza o conteúdo */
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 1rem;
}

thead th {
  cursor: pointer;
  background-color: #f5f5f7;
  padding: 0.5rem;
  text-align: left;
}

tbody td {
  padding: 0.5rem;
  border-top: 1px solid #ddd;
}

tbody tr:nth-child(even) {
  background-color: #f9f9f9;
}

tbody tr:hover {
  background-color: #f1f1f1;
}
</style>
