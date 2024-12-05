<template>
  <div id="app">
    <nav>
      <router-link to="/">Extrato de Transferências</router-link>
      <router-link to="/agendar">Agendar Transferência</router-link>
    </nav>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'App',
};
</script>

<style>
nav {
  display: flex;
  gap: 20px;
  padding: 15px;
  background-color: #007bff;
}
nav a {
  color: white;
  text-decoration: none;
  font-weight: bold;
}
nav a:hover {
  text-decoration: underline;
}
</style>
