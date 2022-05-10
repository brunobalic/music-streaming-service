import Vue from 'vue'
import VueRouter from 'vue-router'

import { authGuard as authGuardUser } from '../auth/authGuard'
import { authGuardArtist } from '@/auth/authGuardArtist'

// imports for public views
import Index from '../views/Index'
import RegisterUser from '../views/RegisterUser'
import RegisterArtist from '../views/RegisterArtist'
import PageNotFound from '../views/PageNotFound'

// artist related imports
import Artist_Base from '../components/artist/Base'
import Artist_Library from '../views/artist/Library'
import Artist_Stats from '../views/artist/Statistics'
import Artist_Upload from '../views/artist/UploadMedia'
import Artist_Album from '../views/artist/Album'
import Artist_Profile from '../views/artist/ProfileSettings'

// user related imports
import User_Base from '../components/regular-user/Base'
import User_Home from '../views/regular-user/Home'
import User_Album from '../views/regular-user/Album'
import User_Artist from '../views/regular-user/Artist'
import User_Discover from '../views/regular-user/Discover'
import User_Playlist from '../views/regular-user/Playlist'
import User_FavListsBase from '../views/regular-user/Favourites'
import User_SearchAdvanced from '@/views/regular-user/SearchAdvanced'
import ListFavAll from '../components/regular-user/Favourites'
import User_CreatePlaylist from '../views/regular-user/CreatePlaylist'

Vue.use(VueRouter)

const routes = [
  //
  // PUBLIC VIEWS
  //
  {
    path: '/welcome',
    name: 'Index',
    component: Index
  },
  {
    path: '/t/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: '/register',
    name: 'u-register',
    component: RegisterUser
  },
  {
    path: '/pro/register',
    name: 'a-register',
    component: RegisterArtist
  },
  {
    path: '/pUser',
    name: 'p1',
    component: Home,
    beforeEnter: authGuardUser
  },
  {
    path: '/pArtist',
    name: 'p2',
    component: Home,
    beforeEnter: authGuardArtist
  },
  //
  // ARTIST
  //
  {
    path: '/pro',
    name: 'artist-base',
    component: Artist_Base,
    beforeEnter: authGuardArtist,
    redirect: { name: 'a-library' },
    children: [
      {
        path: 'library',
        name: 'a-library',
        component: Artist_Library
      },
      {
        path: 'library/album/:id',
        name: 'a-album',
        component: Artist_Album
      },
      {
        path: 'stats',
        name: 'a-stats',
        component: Artist_Stats
      },
      {
        path: 'upload',
        name: 'a-upload',
        component: Artist_Upload
      },
      {
        path: 'profile',
        name: 'a-profile',
        component: Artist_Profile
      }
    ]
  },
  //
  // REGULAR USER
  //
  {
    path: '/user',
    name: 'user-base',
    component: User_Base,
    redirect: { name: 'u-home' },
    beforeEnter: authGuardUser,
    children: [
      {
        path: 'home',
        name: 'u-home',
        component: User_Home,
        beforeEnter: authGuardUser,
      },
      {
        path: 'favourites',
        component: User_FavListsBase,
        beforeEnter: authGuardUser,
        children: [
          {
            path: ':tab?',
            name: 'u-fav-all',
            component: ListFavAll,
            beforeEnter: authGuardUser
          }
        ]
      },
      {
        path: 'search',
        name: 'u-search-advanced',
        component: User_SearchAdvanced,
        beforeEnter: authGuardUser
      },
      {
        path: 'discover',
        name: 'u-discover',
        component: User_Discover,
        beforeEnter: authGuardUser
      },

      {
        path: 'create/playlist',
        name: 'u-create-playlist',
        component: User_CreatePlaylist,
        beforeEnter: authGuardUser
      },
      {
        path: 'artist/:id',
        name: 'u-artist',
        component: User_Artist,
        beforeEnter: authGuardUser
      },
      {
        path: 'album/:id', // id is route param
        name: 'u-album', // name required for route params to work !!!
        component: User_Album,
        beforeEnter: authGuardUser
      },
      {
        path: 'playlist/:id',
        name: 'u-playlist',
        component: User_Playlist,
        beforeEnter: authGuardUser
      }
    ]
  },
  {
    path: '/not-found',
    name: 'page-not-found',
    component: PageNotFound
  },
  {
    path: '*',
    redirect: '/not-found'
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
