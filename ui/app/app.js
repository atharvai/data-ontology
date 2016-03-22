'use strict';

// Declare app level module which depends on views, and components
var app = angular.module('uiApp', ['ngRoute','AppAbout']);
app.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.when('/about', {
            templateUrl: 'components/views/about.html',
            controller: 'AboutCtrl'
        }).otherwise({
            redirectTo: '/'
        });
    }
]);
