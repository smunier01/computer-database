$("#searchbox").typeahead({
    minLength: 3,
    maxItem: 50,
    source: function (query, process) {
        var $this = this;

        $.ajax({
            url: 'http://localhost:8080/cdb/rest/computer/getautocomplete/' + query,
            type: 'GET',
            success: function(data) {
                var reversed = {};
                var suggests = [];
                $.each(data, function(id, elem) {
                    reversed[elem] = elem;
                    // reversed[elem.type] = "Computer";
                    suggests.push(elem);
                });
                this.reversed = reversed;
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