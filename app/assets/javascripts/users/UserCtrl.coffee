
class UserCtrl

    constructor: (@$log, @UserService) ->
        @$log.debug "constructing UserController"
        @users = []
        @getAllUsers()

    getAllUsers: () ->
        @$log.debug "getAllUsers()"

        @UserService.listUsers()
        .then(
            (data) =>
                @$log.debug "Promise returned #{data.length} Users"
                @users = data
            ,
            (error) =>
                @$log.error "Unable to get Users: #{error}"
            )
     
     showRicetta: (@usr) ->
     	@usr.show = true

     hideRicetta: (@usr) ->
     	@usr.show = false
     
     updateRicetta: (usr) ->
     	@UserService.updateRicetta(usr)
     	.then(
     		(data) => 
     			@$log.debug "Updated"
     		,
     		(error) => 
     			@$log.debug "Updated error"
     		)
     		
     deleteRicetta: (usr) ->
     	@UserService.deleteRicetta(usr)
     	.then(
     		(data) => 
     			@$log.debug "Deleted"
     			@getAllUsers()
     		,
     		(error) => 
     			@$log.debug "Deleted error"
     		)	


controllersModule.controller('UserCtrl', UserCtrl)