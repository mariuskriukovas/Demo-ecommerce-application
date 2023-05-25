import {createRouter, createWebHistory} from 'vue-router'
import Inventory from "@/views/tabs/Inventory.vue";
import HomeTabs from "@/views/Home.vue";
import Login from "@/views/Login.vue";
import {useAppStore} from '@/store/app'
import ProductList from "@/views/tabs/product/ProductList.vue";
import ProductView from "@/views/tabs/product/ProductView.vue";

export const LOGIN_ROUTE_NAME = Login.name
export const HOME_ROUTE_NAME = HomeTabs.name
export const INVENTORY_TAB_ROUTE_NAME = Inventory.name
export const PRODUCT_TAB_ROUTE_NAME = ProductList.name
export const PRODUCT_VIEW_ROUTE_NAME = ProductView.name


const routes = [{
  path: '/', component: () => import('@/layouts/default/Default.vue'), children: [{
    path: "/", name: LOGIN_ROUTE_NAME, component: Login,
  }, {
    path: "/home", name: HOME_ROUTE_NAME, component: HomeTabs, children: [{
      path: "inventory-items", name: INVENTORY_TAB_ROUTE_NAME, component: Inventory
    }, {
      path: "products", name: PRODUCT_TAB_ROUTE_NAME, component: ProductList,
    },]
  }, {
    path: "/products/:id/:mode?", name: PRODUCT_VIEW_ROUTE_NAME, component: ProductView
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
