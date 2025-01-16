<template>
  <div class="dashboard">
    <h1>Bem-vindo ao Sistema de Monitorização XYZ!</h1>
    <p>Monitore produtos, sensores e encomendas de forma eficiente.</p>

    <section class="features">
      <!-- Gestão de Produtos -->
      <NuxtLink to="/produtos" class="feature">
        <i class="fas fa-box-open"></i>
        <h3>Gestão de Produtos</h3>
        <p>Adicione, edite e organize seus produtos.</p>
      </NuxtLink>

      <!-- Monitorização de Sensores -->
      <NuxtLink to="/sensores" class="feature">
        <i class="fas fa-microchip"></i>
        <h3>Monitorização de Sensores</h3>
        <p>Acompanhe sensores em tempo real.</p>
      </NuxtLink>

      <!-- Gestão de Encomendas -->
      <NuxtLink to="/encomendas" class="feature">
        <i class="fas fa-truck"></i>
        <h3>Gestão de Encomendas</h3>
        <p>Gerencie as suas encomendas com eficiência.</p>
      </NuxtLink>
    </section>
  </div>
</template>

---

### 🎨 **Estilo Atualizado com Efeito Hover**

```css
<style scoped>
.dashboard {
  padding: 2rem;
  text-align: center;
}

.features {
  display: flex;
  justify-content: center;
  gap: 2rem;
  margin-top: 3rem;
}

/* Estilo dos blocos clicáveis */
.feature {
  width: 250px;
  padding: 1.5rem;
  background-color: #fff;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  text-decoration: none;
  color: inherit;
  text-align: center;
  transition: transform 0.3s, box-shadow 0.3s;
  cursor: pointer;
}

.feature:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
}

/* Ícones */
.feature i {
  font-size: 3rem;
  color: #4caf50;
  margin-bottom: 1rem;
}

/* Títulos */
.feature h3 {
  margin-top: 0.5rem;
  font-size: 1.4rem;
  color: #333;
}

/* Descrição */
.feature p {
  margin-top: 0.5rem;
  color: #555;
}
</style>
