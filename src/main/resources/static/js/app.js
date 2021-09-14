(function(){

  var app = angular.module('notesApp',['ngRoute', 'ngMaterial']);

  app.config(['$locationProvider', '$routeProvider',
      function ($locationProvider, $routeProvider) {

        $routeProvider
          .when('/', {
            templateUrl: '/partials/notes-view.html',
            controller: 'notesController',
          })
          .when('/login', {
             templateUrl: '/partials/login.html',
             controller: 'loginController',
          })
          .otherwise('/');
      }
  ]);

  app.run(['$rootScope', '$location', 'AuthService', function ($rootScope, $location, AuthService) {
      $rootScope.$on('$routeChangeStart', function (event) {

          if ($location.path() == "/login"){
             return;
          }

          if (!AuthService.isLoggedIn()) {
              console.log('DENY');
              event.preventDefault();
              $location.path('/login');
          }
      });
  }]);


  app.service('AuthService', function($http, $location, LocalStorageService){
        let loggedUser = null;

        async function login(username, password){
            try {
                const res = await $http.post("api/login", {username, password});
                loggedUser = res.data.user;
                LocalStorageService.setItem('user', loggedUser);
                return loggedUser;
            } catch(error) {
                loggedUser = null;
                LocalStorageService.clearItem('user');
                throw error;
            }
        }

        function isLoggedIn(){
            const user = LocalStorageService.getItem('user');
            loggedUser = user;
            return !!user;
        }

        function getUser(){
            return loggedUser;
        }

        function logout(){
            loggedUser = null;
            LocalStorageService.clearItem('user');
            $location.path('/login');
        }

        return {
            login,
            isLoggedIn,
            getUser,
            logout
        }
  });

  app.service('LocalStorageService', function(){

    function setItem(key, val){
        localStorage.setItem(key, JSON.stringify(val));
    }

    function getItem(key){
        return JSON.parse(localStorage.getItem(key));
    }

    function clearItem(key){
        localStorage.clear(key);
    }

    return {
        setItem,
        getItem,
        clearItem
    }
});

  app.controller('loginController', function($scope, AuthService, $location){

    $scope.invalidCreds = false;
    $scope.login = {
        username : '',
        password : ''
    };

    $scope.login = function(e){
        e.preventDefault();
        $scope.invalidCreds = false;
        
        AuthService.login($scope.login.username, $scope.login.password).then(function(user){
            $location.path("/");
        }, function(error){
            $scope.invalidCreds = true;
        });
    };
  });


  app.service('NoteService', function($http){
    async function getNotesByUserId (id){
        try {
            const res = await $http.get(`notes/user/${id}`);
            const notes = res.data
            return notes.reverse();
        } catch(error) {
            throw error;
        }
    }

    async function updateNote(note){
        try {
            const res = await $http.put(`notes`, note);
            const notes = res.data
            return notes;
        } catch(error) {
        }
    }

    async function createNote(userId, note){
        try {
            const res = await $http.post(`notes`, {...note, userId});
            const notes = res.data
            return notes;
        } catch(error) {
            throw error;
        }
    }

    return {
        getNotesByUserId,
        updateNote,
        createNote
    }
});

  app.controller('notesController', function($scope, AuthService, NoteService, $location){
    $scope.notes = [];
    $scope.isFetching = true;
    $scope.isEditCreateView = false;
    $scope.selectedNote = null;
    $scope.isNoteUpdating = false;

    const user = AuthService.getUser();
    
    getNotes = function() {
        $scope.isFetching = true;
        NoteService.getNotesByUserId(user.id).then((notes) => {
            $scope.notes = notes;
            $scope.isFetching = false;
            $scope.$apply();
        }).catch(err => {
            $scope.isFetching = false;
            $scope.notes = [];
            $scope.$apply();
        });
    }

    getNotes();

    $scope.onNoteSelect = function(id) {
        $scope.selectedNote = {...$scope.notes.find((note) => note.id == id)}
        $scope.isEditCreateView = true;
    }

    $scope.newNoteView = function(id){
        $scope.selectedNote = { name: '', text: ''};
        $scope.isEditCreateView = true;
    };

    $scope.onNoteSave = async function(noteId) {
        $scope.isNoteUpdating = true;
        if(noteId) {

            const { text, name } = $scope.selectedNote;
            NoteService.updateNote({id: noteId, name, text}).then(res => {
                getNotes();
                $scope.isNoteUpdating = false;
                $scope.selectedNote = null;
                $scope.isEditCreateView = false;
                $scope.$apply();
            })
        } else {
            NoteService.createNote(user.id, $scope.selectedNote).then(res => {
                getNotes()
                $scope.isNoteUpdating = false;
                $scope.selectedNote = null;
                $scope.isEditCreateView = false;
                $scope.$apply();
                
            }).catch(err => {
                $scope.isNoteUpdating = false;
                $scope.selectedNote = null;
                $scope.isEditCreateView = false;
                $scope.$apply();
            });
        }
    }

    $scope.onNoteCancel = function() {
        $scope.selectedNote = null;
        $scope.isEditCreateView = false;
    } 

    $scope.onLogout = function() {
        AuthService.logout();
    }

  });

})();