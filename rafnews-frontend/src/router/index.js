import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Article from "../views/Article";
import Category from "../views/Category";
import MostRead from "../views/MostRead";
import TaggedArticles from "../views/TaggedArticles";
import Login from "../views/Login";
import AdminPanel from "../views/AdminPanel";
import AddArticle from "../views/AddArticle";
import EditArticle from "../views/EditArticle";
import AddUser from "../views/AddUser";
import EditUser from "../views/EditUser";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/article/:id',
    name: 'Article',
    component: Article
  },
  {
    path: '/category/:id',
    name: 'Category',
    component: Category
  },
  {
    path: '/popular',
    name: 'MostRead',
    component: MostRead
  },
  {
    path: '/t/:id',
    name: 'TaggedArticles',
    component: TaggedArticles
  },
  {
    path: '/login',
    name: 'Login',
    meta: {
      isLoggedIn: true
    },
    component: Login
  },
  {
    path: '/cms',
    name: 'AdminPanel',
    meta: {
      authRequired: true
    },
    component: AdminPanel
  },
  {
    path: '/cms/article/add',
    name: 'AddArticle',
    meta: {
      authRequired: true
    },
    component: AddArticle
  },{
    path: '/cms/article/edit/:id',
    name: 'EditArticle',
    meta: {
      authRequired: true
    },
    component: EditArticle
  },
  {
    path: '/cms/user/add',
    name: 'NewUser',
    meta: {
      isAdmin: true
    },
    component: AddUser
  },
  {
    path: '/cms/user/edit/:id',
    name: 'EditUser',
    meta: {
      isAdmin: true
    },
    component: EditUser
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  if (to.meta.authRequired) {
    const jwt = localStorage.getItem('jwt')
    if (!jwt) {
      next({name: 'Login'})
      return
    }
  }

  if (to.meta.isLoggedIn) {
    const jwt = localStorage.getItem('jwt')
    if (jwt) {
      next({name: 'AdminPanel'})
      return
    }
  }

  if (to.meta.isAdmin) {
    const jwt = localStorage.getItem('jwt')
    if (jwt) {
      const payload = JSON.parse(atob(jwt.split('.')[1]))
      if (payload.role !== 'ADMIN') {
        next({name: 'AdminPanel'})
        return
      }
    } else {
      next({name: 'Login'})
    }
  }

  next()
})

export default router
