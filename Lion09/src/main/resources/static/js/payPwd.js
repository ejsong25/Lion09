$(function(){
    $(document).on("keypress keyup keydown", "input[onlyNumber]", function(e){
        console.log(e.which);
        if(/[a-z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g.test(this.value)){ //한글 막기
            e.preventDefault();
            this.value = "";
        }else if (e.which != 8 && e.which != 0 //영문 e막기
            && e.which < 48 || e.which > 57    //숫자키만 받기
            && e.which < 96 || e.which > 105){ //텐키 받기
            e.preventDefault();
            this.value = "";
        }else if (this.value.length >= this.maxLength){ //1자리 이상 입력되면 다음 input으로 이동시키기
            this.value = this.value.slice(0, this.maxLength);
            if($(this).next("input").length > 0){
                $(this).next().focus();
            }else{
                $(this).blur();
            }
        }
        
        // 6자리인 경우에만 확인 버튼 활성화
        const $inputFields = $("input[onlyNumber]"); // 숫자 입력란들을 모두 선택
        var totalLength = $inputFields.toArray().reduce(function(sum, input) {
            return sum + input.value.length;
        }, 0);
        if (totalLength === 6) {
             $("#btnCheck, #btnChange, #btnRegister").prop("disabled", false);
        } else {
            $("#btnCheck, #btnChange, #btnRegister").prop("disabled", true);
        }
        
    });
    
});