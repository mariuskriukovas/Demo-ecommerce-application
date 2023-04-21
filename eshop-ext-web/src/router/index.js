import {createRouter, createWebHistory} from 'vue-router'
import Inventory from "@/views/tabs/Inventory.vue";
import Products from "@/views/tabs/Product.vue";
import HomeTabs from "@/views/Home.vue";
import Login from "@/views/Login.vue";
import {useAppStore} from '@/store/app'

const routes = [
  {
    path: '/',
    component: () => import('@/layouts/default/Default.vue'),
    children: [
      {
        path: "/",
        name: Login.name,
        component: Login,
      },
      {
        path: "/home",
        name: HomeTabs.name,
        component: HomeTabs,
        children: [
          {
            path: "",
            name: Inventory.name,
            component: Inventory
          },
          {
            path: "products",
            name: Products.name,
            component: Products
          }
        ]
      }
    ],
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
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
