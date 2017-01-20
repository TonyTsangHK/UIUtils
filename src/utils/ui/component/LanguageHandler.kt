package utils.ui.component

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
        EN_US("en-us"), ZH_HK("zh-hk");
        
        override fun toString(): String {
            return lang
        }
    }
    
    private val DEFAULT_LANG = LANG.ZH_HK
    private var currentLang = DEFAULT_LANG
    private var constantHandler: ConstantHandler = ConstantFactory.getConstantHandler(
        LanguageHandler.javaClass, "lang-$currentLang.json"
    )!!

    /**
     * Set the language variable, this should be called before any UI component initialization
     * 
     * @param lang lang code, default to zh-hk
     */
    @JvmStatic
    fun setLang(lang: LANG) {
        when(lang) {
            LANG.EN_US -> {
                currentLang = lang
                constantHandler = ConstantFactory.getConstantHandler(
                    LanguageHandler.javaClass, "lang-${lang.lang}.json"
                )!!
            }
            else -> {
                currentLang = DEFAULT_LANG
                constantHandler = ConstantFactory.getConstantHandler(
                    LanguageHandler.javaClass, "lang-${DEFAULT_LANG.lang}.json"
                )!!
            }
        }
    }
    
    @JvmStatic
    fun variable(key: String): String {
        return constantHandler.getConstantStringValue(key, true)!!
    }
}