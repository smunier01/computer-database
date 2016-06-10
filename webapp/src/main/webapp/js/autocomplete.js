$("#searchbox").typeahead({
    //input: '#searchbox',
    minLength: 1,
    // maxItem: 20,
    // source: {
    //     "computer": function(query, process) {
    //         var $this = this;
    //         console.log("computer");
    //         $.ajax({
    //             url: 'http://localhost:8080/cdb/rest/computer/getautocomplete/' + query,
    //             type: 'GET',
    //             success: function(data) {
    //                 console.log("success ajax computer");
    //                 var reversed = {};
    //                 var suggests = [];
    //                 $.each(data, function(id, elem) {
    //                     reversed[elem] = elem;
    //                     suggests.push(elem);
    //                 });
    //                 this.reversed = reversed;
    //                 console.log(suggests);
    //                 process(suggests);
    //             }
    //         });
    //     },
    //     "company": function(query, process) {
    //         var $this = this;
    //         console.log("company");
    //         $.ajax({
    //             url: 'http://localhost:8080/cdb/rest/company/getautocomplete/' + query,
    //             type: 'GET',
    //             success: function(data) {
    //                 console.log("success ajax company");
    //                 var reversed = {};
    //                 var suggests = [];
    //                 $.each(data, function(id, elem) {
    //                     reversed[elem] = elem;
    //                     suggests.push(elem);
    //                 });
    //                 this.reversed = reversed;
    //                 console.log(suggests);
    //                 process(suggests);
    //             }
    //         });
    //     }
    // },
    source: function (query, process) {
        var $this = this;

        $.ajax({
            url: 'http://localhost:8080/cdb/rest/computer/getautocomplete/' + query,
            type: 'GET',
            success: function(data) {
                var reversed = {};
                var suggests = [];
                $.each(data, function(id, elem) {
                    reversed[elem] = elem + " (Computer)";
                    suggests.push(elem+ " (Computer)");
                });
                this.reversed = reversed;
                console.log(suggests);
                process(suggests);
            }
        });

        $.ajax({
            url: 'http://localhost:8080/cdb/rest/company/getautocomplete/' + query,
            type: 'GET',
            success: function(data) {
                var reversed = {};
                var suggests = [];
                $.each(data, function(id, elem) {
                    reversed[elem] = elem+ " (Company)";
                    suggests.push(elem)+ " (Company)";
                });
                this.reversed = this.reversed.concat(reversed);
                console.log(suggests);
                process(suggests);
            }
        });
    },
    updater: function(item) {
        $("#searchbox").val(item);
        return item;
    },
    matcher: function() {
        return true;
    }
});