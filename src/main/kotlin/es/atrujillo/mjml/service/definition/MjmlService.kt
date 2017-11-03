package es.atrujillo.mjml.service.definition

interface MjmlService {
    fun transpileMjmlToHtml(mjmlBody: String, mjmlAuthConf: MjmlAuthConf): String
}
