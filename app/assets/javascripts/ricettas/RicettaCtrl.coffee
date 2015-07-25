
class RicettaCtrl

    constructor: (@$log, @RicettaService) ->
        @ricettas = []
        @getAllRicettas()

    getAllRicettas: () ->
    	@RicettaService.listRicettas()
        .then(
            (data) =>
                @ricettas = data
            ,
            (error) =>
                @$log.error "Unable to get Ricetta: #{error}"
            )

    deleteRicetta: (@ricetta) ->
    	@RicettaService.deleteRicetta(@ricetta)
        .then(
            (data) =>
                @getAllRicettas()
            ,
            (error) =>
                @$log.error "Unable to get Ricetta: #{error}"
            )
    
    updateRicetta: (@ricetta) ->
    	@RicettaService.updateRicetta(@ricetta)
        .then(
            (data) =>
                @$log.error "Updated"
            ,
            (error) =>
                @$log.error "Unable to get Ricetta: #{error}"
            )
	
    showRicetta: (@ricetta) ->
          @ricetta.show = true

    hideRicetta: (@ricetta) ->
          @ricetta.show = false
	

	

controllersModule.controller('RicettaCtrl', RicettaCtrl)