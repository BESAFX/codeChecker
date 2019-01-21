"use strict";




var charts = {
    init: function(){
        // Sparkline                    
        if($(".sparkline").length > 0)
           $(".sparkline").sparkline('html', { enableTagOptions: true,disableHiddenCheck: true});              
       // End sparkline
    }
};


var knob = {
    init: function(){
        if($(".knob").length > 0)
            $(".knob").knob();        
    }
};



$(function(){    

    knob.init();

    charts.init();
 
});