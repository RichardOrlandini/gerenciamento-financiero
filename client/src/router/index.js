import { createRouter, createWebHistory } from 'vue-router';
import TransferenciaForm from '@/components/TransferenciaForm.vue';
import TransferenciaList from '@/components/TransferenciaList.vue';

const routes = [
  {
    path: '/',
    name: 'TransferenciaList',
    component: TransferenciaList,
  },
  {
    path: '/agendar',
    name: 'TransferenciaForm',
    component: TransferenciaForm,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
