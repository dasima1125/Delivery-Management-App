package com.example.galileo.dataClass

class Delivery_User{

    var id    : Int     = 0
    var demo1 : String = ""//송장번호 택배사
    var demo2 : String = ""// 현재 정보
    var demo3 : String = ""//사용자 정보

    constructor(a1: String, a2: String, a3: String)
    {
        this.demo1 = a1
        this.demo2 = a2
        this.demo3 = a3
    }
    constructor() {

    }

}
class Delivery_User2 {
    // alpha 테이블 열
    var id: Long  = 0
    var baseInfo   : String = ""
    var nowInfo    : String = ""

    // records 테이블 열
    var recordsId     : Long = 0
    var deliveryNum   : Long  = 0
    var description   : String = ""

    constructor(
        alphaId      :     Long,
        baseInfo     :   String,
        nowInfo      :   String,
        recordsId    :     Long,
        deliveryNum  :     Long,
        description  :   String
    ) {
        this.id          =      alphaId
        this.baseInfo    =     baseInfo
        this.nowInfo     =      nowInfo
        this.recordsId   =    recordsId
        this.deliveryNum =  deliveryNum
        this.description =  description
    }

    constructor() {
    }
}

class Delivery_User3 {
    // alpha 테이블 열
    var id: Long  = 0
    var baseInfo   : String = ""
    var nowInfo    : String = ""
    var totalInfo  : String = ""

    constructor(
        alphaId      :     Long,
        baseInfo     :   String,
        nowInfo      :   String,
        totalInfo    :   String

    ) {
        this.id          =      alphaId
        this.baseInfo    =     baseInfo
        this.nowInfo     =      nowInfo
        this.totalInfo   =      totalInfo
    }
    constructor() {
    }
}

class widget_test{
    //alphawidget용
    var id:Long = 0

    constructor(
        id : Long
    ) {
        this.id = id
    }
}
