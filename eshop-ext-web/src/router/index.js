import {createRouter, createWebHistory} from 'vue-router'
import Inventory from "@/views/tabs/Inventory.vue";
import HomeTabs from "@/views/Home.vue";
import Login from "@/views/Login.vue";
import {useAppStore} from '@/store/app'
import ProductList from "@/views/tabs/product/ProductList.vue";

const routes = [{
  path: '/', component: () => import('@/layouts/default/Default.vue'), children: [{
    path: "/", name: Login.name, component: Login,
  }, {
    path: "/home", name: HomeTabs.name, component: HomeTabs, children: [{
      path: "inventory-items", name: Inventory.name, component: Inventory
    }, {
      path: "products", name: ProductList.name, component: ProductList,
    },]
  }],
},]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL), routes,
})
router.beforeEach((to, from, next) => {
  const store = useAppStore()
  if (to.name !== Login.name && !store.isAuthenticated) {
    next({name: Login.name})
  } else {
    next()
  }
})

export default router