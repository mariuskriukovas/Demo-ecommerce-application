import {createRouter, createWebHistory} from 'vue-router'
import Inventory from "@/views/tabs/Inventory.vue";
import HomeTabs from "@/views/Home.vue";
import Login from "@/views/Login.vue";
import {useAppStore} from '@/store/app'
import ProductList from "@/views/tabs/product/ProductList.vue";
import ProductView from "@/views/tabs/product/ProductView.vue";
import SignUp from "@/views/SignUp.vue";
import PublicProductList from "@/views/tabs/public/PublicProductList.vue";

export const LOGIN_ROUTE_NAME = Login.name
export const HOME_ROUTE_NAME = HomeTabs.name
export const INVENTORY_TAB_ROUTE_NAME = Inventory.name
export const PRODUCT_TAB_ROUTE_NAME = ProductList.name
export const PUBLIC_PRODUCT_TAB_ROUTE_NAME = PublicProductList.name
export const PRODUCT_VIEW_ROUTE_NAME = ProductView.name
export const SIGN_UP_ROUTE_NAME = SignUp.name


const routes = [{
  path: '/', component: () => import('@/layouts/default/Default.vue'), children: [{
    path: "/sign-up", name: SIGN_UP_ROUTE_NAME, component: SignUp,
  }, {
    path: "/", name: LOGIN_ROUTE_NAME, component: Login,
  }, {
    path: "/home", name: HOME_ROUTE_NAME, component: HomeTabs, children: [{
      path: "inventory-items", name: INVENTORY_TAB_ROUTE_NAME, component: Inventory
    }, {
      path: "products", name: PRODUCT_TAB_ROUTE_NAME, component: ProductList,
    }, {
      path: "public-products", name: PUBLIC_PRODUCT_TAB_ROUTE_NAME, component: PublicProductList,
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

  const publicRoutes = [LOGIN_ROUTE_NAME, SIGN_UP_ROUTE_NAME]

  if (!store.isAuthenticated) {
    if (publicRoutes.includes(to.name)) {
      next()
    } else {
      next({name: LOGIN_ROUTE_NAME})
    }
  } else {
    next()
  }
})

export default router
