Feature: It Högskolan hemsida
  Testa funktioner, templates och knappar

  Background:
    Given att jag är på hemsidan "https://iths.se"
    And jag accepterar cookies

  Scenario: IT-Högskolans logotyp
    Then jag ska se IT-Högskolan logotypen på hemsidan

  Scenario: Användaren kontrollerar favicon på startsidan
    Then ska favicon vara närvarande med den förväntade länken

  Scenario Outline: Webbplatsen ska vara responsiv och anpassa sig till olika skärmupplösningar
    When upplösningen är <width>x<height>
    Then sidan ska korrekt anpassa sig till upplösningen <width>x<height>

    Examples:
      | width | height |
      | 390   | 844    |
      | 1024  | 1366   |
      | 800   | 600    |

  Scenario: Titel stämmer
    Then skall titeln vara "IT-Högskolan – Här startar din IT-karriär!"

  Scenario Outline: Verifiera kontaktuppgifter för olika städer
    When jag scrollar till footer-sektionen
    Then borde jag se "<Stad>" sektionen i footern
    Then emailadressen borde vara "<Email>" i "<Stad>" sektionen
    Then telefonnumret borde vara "<Telefon>" i "<Stad>" sektionen
    Then adressen borde vara "<Adress>" i "<Stad>" sektionen

    Examples:
      | Stad      | Email        | Telefon       | Adress                    |
      | Göteborg  | info@iths.se | 031-790 42 55 | Ebbe Lieberathsgatan 18 c |
      | Stockholm | info@iths.se | 08-557 683 53 | Trekantsvägen 1           |

  Scenario: Klickar mig fram i Utbildningar
    And i hero sektionen hittar jag knappen "Alla utbildningar"
    When jag dubbelklickar på "Alla utbildningar"
    Then hamnar jag på sidan "https://www.iths.se/utbildningar/"

  Scenario: Klickar mig fram i quizet
    Given att jag är på hemsidan "https://iths.se"
    And jag accepterar cookies
    And skrollar ner tills jag ser knappen "Gör quizet"
    When jag klickar på knappen "Gör quizet"
    Then hamnar jag på sidan som innehåller "vilken-utbildning-passar-mig" med texten "Testa vår utbildningskompass!"
    And jag svarar på frågorna i quizet
      | fråga                                                         | svar                                                                                 |
      | Vad lockar dig mest till IT-världen?                          | Att utforska nya teknologier som artificiell intelligens och molntjänster.           |
      | Vilket ord beskriver dig bäst?                                | Kreativ.                                                                             |
      | Vilken aktivitet lockar dig mest?                             | Skapa och designa grafik och användargränssnitt.                                     |
      | Vad är viktigast för dig när det gäller teknologi?            | Att den är användbar och löser verkliga problem.                                     |
      | Vad lockar dig mest till en karriär inom IT?                  | Möjligheten att skapa produkter och upplevelser som människor älskar att använda.    |
      | Vilket område tror du skulle vara mest spännande att utforska?| Grafisk design och användarupplevelse.                                               |
      | Vilken aktivitet föredrar du mest när du arbetar med andra?   | Brainstorma och generera nya idéer.                                                  |
      | Vilket typ av projekt tycker du bäst om att arbeta med?       | Utveckla innovativa lösningar som förbättrar användarupplevelsen.                    |
      | Vad är ditt mål inom IT-branschen?                            | Att bli en ledande expert inom mitt område.                                          |
      | Vad inspirerar dig mest i ditt arbete?                        | Att kunna påverka och göra skillnad genom teknik.                                    |
    And jag skriver in en mailaddress "test@example.com"
    And jag klickar på formulärknappen "See results"
    Then jag ser resultaten av quizet


  Scenario: SEO-relevant metadata från IT-Högskolan homepage
    Then skall titeln vara "IT-Högskolan – Här startar din IT-karriär!"
    And meta description ska vara närvarande
    And meta robots tag ska vara närvarande
    And canonical link ska vara närvarande

  Scenario: Kontrollera kurslistan
    When jag navigerar till sidan "https://iths.se/utbildningar"
    Then ska jag se en lista med kurser
    And listan ska innehålla minst 5 kurser

  Scenario: Spela upp video från informationssidan
    When jag klickar på menyalternativet "Hur du ansöker"
    Then ska jag navigeras till sidan "https://www.iths.se/om-oss/"
    When jag skrollar ner till videolänken
    And jag klickar på play-knappen
    Then ska videon spelas upp

  Scenario: Kontakta via mailto från informationssida
    When jag klickar på menyalternativet "För studerande"
    Then ska jag navigeras till sidan "https://www.iths.se/kontakt/informationstraffar/"
    When jag skrollar ner till "info@iths.se"
    Then ska länken öppna mailto-klienten

  Scenario: Meny-navigation
    When jag klickar på menyalternativet "Utbildningar"
    Then ska jag navigeras till sidan "https://iths.se/utbildningar"
    And titeln ska vara "Utbildningar - IT-Högskolan"
