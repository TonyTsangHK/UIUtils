package utils.ui.lang

import utils.template.ResourceLoader
import utils.template.constant.ConstantFactory
import utils.template.constant.ConstantHandler

/**
 * Created with IntelliJ IDEA.
 * User: Tony Tsang
 * Date: 2017-01-20
 * Time: 09:28
 */
object LanguageHandler {
    enum class LANG(val lang: String) {
        EN_US("en_US"), ZH_HK("zh_HK"), ZH_CN("zh_CN");
        
        override fun toString(): String {
            return lang
        }
    }
    
    private val DEFAULT_LANG = LANG.ZH_HK
    private var currentLang = DEFAULT_LANG
    private var constantHandler: ConstantHandler = ConstantFactory.getConstantHandler(
        ResourceLoader("lang"), "lang-${currentLang}.json"
    )!!

    /**
     * Set the language variable, this should be called before any UI component initialization
     * 
     * @param lang lang code, default to zh-hk
     */
    @JvmStatic
    fun setLang(lang: LANG) {
        // since lang is restricted by LANG enum class, exceptional case handling code is not required. 
        currentLang = lang
        constantHandler = ConstantFactory.getConstantHandler(
            ResourceLoader("lang"), "lang-${lang.lang}.json"
        )!!
    }
    
    fun setLang(lang: String) {
        for (lg in LANG.values()) {
            if (lg.lang == lang) {
                setLang(lg)
                // Done with setLang, return
                return
            }
        }
        
        // Unknown lang, use default
        setLang(DEFAULT_LANG)
    }
    
    @JvmStatic
    fun variable(key: String): String {
        return constantHandler.getConstantStringValue(key, true)!!
    }
}