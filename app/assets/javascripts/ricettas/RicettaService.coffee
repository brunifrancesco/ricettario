
class RicettaService

    @headers = {'Accept': 'application/json', 'Content-Type': 'application/json'}
    @defaultConfig = { headers: @headers }

    constructor: (@$log, @$http, @$q) ->
        @$log.debug "constructing RicettaService"

    listRicettas: () ->
        deferred = @$q.defer()

        @$http.get("/recipes")
        .success((data, status, headers) =>
                deferred.resolve(data)
            )
        .error((data, status, headers) =>
                deferred.reject(data)
            )
        deferred.promise
    
    deleteRicetta: (ricetta) ->
        deferred = @$q.defer()

        @$http.delete('/recipe/'+ricetta.name, ricetta)
        .success((data, status, headers) =>
              
                deferred.resolve(data)
            )
        .error((data, status, headers) =>
              
                deferred.reject(data)
            )
        deferred.promise

    updateRicetta: (ricetta) ->
        deferred = @$q.defer()

        @$http.put('/recipe', ricetta)
        .success((data, status, headers) =>
              
                deferred.resolve(data)
            )
        .error((data, status, headers) =>
              
                deferred.reject(data)
            )
        deferred.promise

    createRicetta: (ricetta) ->
        deferred = @$q.defer()

        @$http.post('/recipe', ricetta)
        .success((data, status, headers) =>
              
                deferred.resolve(data)
            )
        .error((data, status, headers) =>
              
                deferred.reject(data)
            )
        deferred.promise     
    
	
    
	
servicesModule.service('RicettaService', RicettaService)