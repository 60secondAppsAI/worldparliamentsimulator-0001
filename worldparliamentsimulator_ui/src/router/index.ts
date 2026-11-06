import { createRouter, createWebHashHistory } from 'vue-router';
import { useAuth } from '../stores/auth';
import SignIn from '../components/SignIn.vue';
import Settings from '../components/Settings.vue';
import MyDashboard from '../components/MyDashboard.vue';

// Dynamic imports for module components
import Countrys from '../components/Countrys.vue';
import CountryDetail from '../components/CountryDetail.vue';
import Leaders from '../components/Leaders.vue';
import LeaderDetail from '../components/LeaderDetail.vue';
import Ideologys from '../components/Ideologys.vue';
import IdeologyDetail from '../components/IdeologyDetail.vue';
import Peoples from '../components/Peoples.vue';
import PeopleDetail from '../components/PeopleDetail.vue';
import Economys from '../components/Economys.vue';
import EconomyDetail from '../components/EconomyDetail.vue';
import Militarys from '../components/Militarys.vue';
import MilitaryDetail from '../components/MilitaryDetail.vue';
import Infrastructures from '../components/Infrastructures.vue';
import InfrastructureDetail from '../components/InfrastructureDetail.vue';
import ContinentProblems from '../components/ContinentProblems.vue';
import ContinentProblemDetail from '../components/ContinentProblemDetail.vue';
import Alliances from '../components/Alliances.vue';
import AllianceDetail from '../components/AllianceDetail.vue';
import NuclearPolicys from '../components/NuclearPolicys.vue';
import NuclearPolicyDetail from '../components/NuclearPolicyDetail.vue';
import Diplomacys from '../components/Diplomacys.vue';
import DiplomacyDetail from '../components/DiplomacyDetail.vue';
import Espionages from '../components/Espionages.vue';
import EspionageDetail from '../components/EspionageDetail.vue';
import Organizations from '../components/Organizations.vue';
import OrganizationDetail from '../components/OrganizationDetail.vue';
import Events from '../components/Events.vue';
import EventDetail from '../components/EventDetail.vue';
import Communications from '../components/Communications.vue';
import CommunicationDetail from '../components/CommunicationDetail.vue';
import Turns from '../components/Turns.vue';
import TurnDetail from '../components/TurnDetail.vue';

const routes = [
  {
    path: '/',
    name: 'home',
    redirect: '/dashboard'
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: MyDashboard
  },
  
  // Dynamic module routes
  {
    path: '/countrys',
    name: 'Countrys',
    component: Countrys
  },
  {
    path: '/country/:id',
    name: 'CountryDetail',
    component: CountryDetail,
    props: true
  },
  {
    path: '/leaders',
    name: 'Leaders',
    component: Leaders
  },
  {
    path: '/leader/:id',
    name: 'LeaderDetail',
    component: LeaderDetail,
    props: true
  },
  {
    path: '/ideologys',
    name: 'Ideologys',
    component: Ideologys
  },
  {
    path: '/ideology/:id',
    name: 'IdeologyDetail',
    component: IdeologyDetail,
    props: true
  },
  {
    path: '/peoples',
    name: 'Peoples',
    component: Peoples
  },
  {
    path: '/people/:id',
    name: 'PeopleDetail',
    component: PeopleDetail,
    props: true
  },
  {
    path: '/economys',
    name: 'Economys',
    component: Economys
  },
  {
    path: '/economy/:id',
    name: 'EconomyDetail',
    component: EconomyDetail,
    props: true
  },
  {
    path: '/militarys',
    name: 'Militarys',
    component: Militarys
  },
  {
    path: '/military/:id',
    name: 'MilitaryDetail',
    component: MilitaryDetail,
    props: true
  },
  {
    path: '/infrastructures',
    name: 'Infrastructures',
    component: Infrastructures
  },
  {
    path: '/infrastructure/:id',
    name: 'InfrastructureDetail',
    component: InfrastructureDetail,
    props: true
  },
  {
    path: '/continentProblems',
    name: 'ContinentProblems',
    component: ContinentProblems
  },
  {
    path: '/continentProblem/:id',
    name: 'ContinentProblemDetail',
    component: ContinentProblemDetail,
    props: true
  },
  {
    path: '/alliances',
    name: 'Alliances',
    component: Alliances
  },
  {
    path: '/alliance/:id',
    name: 'AllianceDetail',
    component: AllianceDetail,
    props: true
  },
  {
    path: '/nuclearPolicys',
    name: 'NuclearPolicys',
    component: NuclearPolicys
  },
  {
    path: '/nuclearPolicy/:id',
    name: 'NuclearPolicyDetail',
    component: NuclearPolicyDetail,
    props: true
  },
  {
    path: '/diplomacys',
    name: 'Diplomacys',
    component: Diplomacys
  },
  {
    path: '/diplomacy/:id',
    name: 'DiplomacyDetail',
    component: DiplomacyDetail,
    props: true
  },
  {
    path: '/espionages',
    name: 'Espionages',
    component: Espionages
  },
  {
    path: '/espionage/:id',
    name: 'EspionageDetail',
    component: EspionageDetail,
    props: true
  },
  {
    path: '/organizations',
    name: 'Organizations',
    component: Organizations
  },
  {
    path: '/organization/:id',
    name: 'OrganizationDetail',
    component: OrganizationDetail,
    props: true
  },
  {
    path: '/events',
    name: 'Events',
    component: Events
  },
  {
    path: '/event/:id',
    name: 'EventDetail',
    component: EventDetail,
    props: true
  },
  {
    path: '/communications',
    name: 'Communications',
    component: Communications
  },
  {
    path: '/communication/:id',
    name: 'CommunicationDetail',
    component: CommunicationDetail,
    props: true
  },
  {
    path: '/turns',
    name: 'Turns',
    component: Turns
  },
  {
    path: '/turn/:id',
    name: 'TurnDetail',
    component: TurnDetail,
    props: true
  },

  // Core application routes
  {
    path: '/signin',
    name: 'SignIn',
    component: SignIn
  },
//  {
//    path: '/users',
//    name: 'Users',
//    component: Users,
//    meta: { requiresAuth: true }
//  },
  {
    path: '/settings',
    name: 'Settings',
    component: Settings,
    meta: { requiresAuth: true }
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/dashboard'
  }
];

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes,
  scrollBehavior(to, from, savedPosition) {
    return savedPosition || { top: 0 };
  }
});

// Navigation guard for authentication
router.beforeEach((to, from, next) => {
  const auth = useAuth();
  
  if (to.meta.requiresAuth && !auth.isAuthenticated) {
    next({ name: 'SignIn', query: { redirect: to.fullPath } });
  } else if (to.name === 'SignIn' && auth.isAuthenticated) {
    next({ name: 'Dashboard' });
  } else {
    next();
  }
});

export default router;
