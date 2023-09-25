require: slotfilling/slotFilling.sc
  module = sys.zb-common
theme: /

    state: Start
        q: $regex</start>
        q!: * (*начало*) *
        a: Приветствуем в сети кинотеатров "Окулус"! Вас интересует кинотеатр в Москве или Санкт-Петербурге?
        buttons:
            "Санкт-Петербург" -> /questionSpb
            "Москва" -> /questionMsk
        
    state: Okay
            a: Заказ отменён. Деньги будут возвращены на карту в течение трёх рабочих дней. Для возврата к началу, напишите "Начало"
            
    state: ticketRefundSpbFinal
        InputText:
            prompt = Введите номер заказа для отмены
            varName = refund
            then = /Okay


        
    state: questionSpb
        q: * (*верн*/*возвр*) * || toState = "ticketRefundSpb"
        q: * ([*куп*/*вз*] [*билет*]) * ||toState = "ticketSpb" 
        q: * (*афиш*/*фильм*/*смотр*) * ||toState = "AfishaSpb"
        q: * (*найд*/*адрес*) * ||toState = "addrSpb"
        a: Выбранный город - Санкт-Петербург, какой у Вас вопрос?
        
        state: ticketSpb
            a: Приобрести билет на интересующий вас сеанс можно на нашем сайте, выбрав фильм на странице афиши, либо обратиться в кассу нашей сети кинотеатров
            buttons: 
                "Адреса кинотеатров в Санкт-Петербурге" -> /questionSpb/addrSpb
                "Назад" -> /questionSpb
                
        state: addrSpb
            a: В Санкт-Петербурге есть два кинотеатра 'Окулус': 
                \n* 'Окулус' на Васильевском острове: Донская улица, дом 25
                \n* 'Окулус' на Невском: Невский проспект, дом 52
            buttons: 
                "Назад" -> /questionSpb 
                
        state: AfishaSpb
            a: В наших кинотеатрах вы можете посмотреть следующие фильмы:
            image: https://www.themoviedb.org/t/p/original/qqwqW8jWjZ6iaLRU98z3fdghwRn.jpg
            image: https://www.themoviedb.org/t/p/original/8OQzw8keE6sDNH25sOqPRTxhFTO.jpg
            image: https://www.themoviedb.org/t/p/original/jzwrEZ8CaXpFDQaQJjmAtFBoL3J.jpg
            buttons: 
                "Адреса кинотеатров в Санкт-Петербурге" -> /questionSpb/addrSpb
                "Назад" -> /questionSpb    
            
        state: ticketRefundSpb
            Confirmation:
                prompt = Вы хотите вернуть билеты?
                agreeState = /ticketRefundSpbFinal
                disagreeState = /Start
                useButtons = true
                agreeButton = Да, я хочу вернуть билеты
                disagreeButton = Нет, вернуться к старту
                
                
    state: OkayMsk
            a: Заказ отменён. Деньги будут возвращены на карту в течение трёх рабочих дней. Для возврата к началу, напишите "Начало"
    state: ticketRefundMskFinal
        InputText:
            prompt = Введите номер заказа для отмены
            varName = refund
            then = /OkayMsk


        
    state: questionMsk
        q: * (*верн*/*возвр*) * || toState = "ticketRefundMsk"
        q: * ([*куп*/*вз*] [*билет*]) * ||toState = "ticketMsk"
        q: * (*афиш*/*фильм*/*смотр*) * ||toState = "AfishaMsk"
        q: * (*найд*/*адрес*) * ||toState = "addrMsk"
        a: Выбранный город - Москва, какой у Вас вопрос?

        state: addrMsk
            a: В Москве есть три кинотеатра 'Окулус':
                \n* 'Окулус' на Охотном ряду: Тверская улица, дом 4
                \n* 'Окулус' на ЦСКА: Ходынский бульвар, дом 4
                \n* 'Окулус' на Шаболовской: Донская улица, дом 8  
            buttons: 
                "Назад" -> /questionMsk  
        state: ticketMsk
            a: Приобрести билет на интересующий вас сеанс можно на нашем сайте, выбрав фильм на странице афиши, либо обратиться в кассу нашей сети кинотеатров
            buttons: 
                "Адреса кинотеатров в Москве" -> /questionMsk/addrMsk
                "Назад" -> /questionMsk
                
                
        state: AfishaMsk
            a: В наших кинотеатрах вы можете посмотреть следующие фильмы:
            image: https://www.themoviedb.org/t/p/original/qqwqW8jWjZ6iaLRU98z3fdghwRn.jpg
            image: https://www.themoviedb.org/t/p/original/8OQzw8keE6sDNH25sOqPRTxhFTO.jpg
            image: https://www.themoviedb.org/t/p/original/jzwrEZ8CaXpFDQaQJjmAtFBoL3J.jpg
            buttons: 
                "Адреса кинотеатров в Москве" -> /questionMsk/addrMsk
                "Назад" -> /questionMsk   
            
        state: ticketRefundMsk
            Confirmation:
                prompt = Вы хотите вернуть билеты?
                agreeState = /ticketRefundMskFinal
                disagreeState = /Start
                useButtons = true
                agreeButton = Да, я хочу вернуть билеты
                disagreeButton = Нет, вернуться к старту
                
        
    state: NoMatch
        event!: noMatch
        a: Я не понял. Вы сказали: {{$request.query}}. Чтобы вернуться в начало, напишите "Начало"


    state: Match
        event!: match
        a: {{$context.intent.answer}}