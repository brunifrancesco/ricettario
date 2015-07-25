
class CreateRicettaCtrl

    constructor: (@$log, @$location,  @RicettaService) ->
        @ricetta = {}

    createRicetta: () ->
        @RicettaService.createRicetta(@ricetta)
        .then(
            (data) =>
                @ricetta = data
                @$location.path("/")
            ,
            (error) =>
                @$log.error "Unable to create Ricetta: #{error}"
            )

controllersModule.controller('CreateRicettaCtrl', CreateRicettaCtrl)