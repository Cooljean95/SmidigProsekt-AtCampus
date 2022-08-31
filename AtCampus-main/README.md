[![Java CI with Maven](https://github.com/ToMrArcher/AtCampus/actions/workflows/maven.yml/badge.svg)](https://github.com/ToMrArcher/AtCampus/actions/workflows/maven.yml)
# AtCampus 

[Link to GitHub Pages](https://tomrarcher.github.io/atcampus-slate) <br>
[Link to Slate Github](https://github.com/tomrarcher/atcampus-slate)


# Installing and running the application:

Vær obs på å ikke ha en annen instanse av Postgres kjørende lokalt

1. Ha Docker Desktop installert (https://www.docker.com/get-started/)
2. Naviger til /Server/src/main/resources/docker
3. Kjør kommandoen "docker-compose up" (for og fjerne denne instansen, skriver man "docker-compose down")
3. Åpne /Server prosjektet/mappen i IntelliJ eller preferet Java IDE
4. Kjør applikasjonen (ServerApplication)
5. Finn IPv4 addressen til din enhet som er koblet til nettverket. (Windows: "ipconfig" i konsollen, Max/Linux: "ifconfig" i konsollen)
6. Nå skal backend være klar til å brukes, og vi hopper videre til /Client.
7. Kjør "npm install" for å laste ned alle dependencies til prosjektet.
8. Åpne filen /Client/services/AxiosService.js og endre IPv4 addressen til din addresse på linje 7
9. Kjør "npm run start" for å starte Expo.
10. Last ned "Expo Go" appen fra Apple AppStore/Google PlayStore
11. Deretter skal det bare være og skanne QR-koden i nettsiden som åpnes, så skal appen åpnes i Expo appen.

Siden mobilenheten og Spring backenden kjøres på forskjellig enhet må man bruke backenden sin lokale IP-addresse for å koble de sammen.
Et alternativ er å bruke vår offentlige instanse av backenden, hvor linje 8 og 9 i AxiosService.js må endres til dette:

```js
//const API_URL = `http://${IP}:8080/api/`
const API_URL = "https://fierce-plains-44298.herokuapp.com/api/"
```

## Database Diagram

### Database Diagram
![alt text](https://www.plantuml.com/plantuml/png/hLHDRzGm4BtdL_ZMWXIsV5IWI8WY97e0LQY8S-HDPdTTsPvWEuMgs__EEhEErzwwuj2zh3plvUNvpKmkhEF6ZKfM9nld1jii5eFasbTUTQWMkBo1pjMzu6l3rKbLWNR2tR5V5eozhzZkzvADkqCh-eP9r6ls-P9zce2wzd-4heInhjLSGSDkkUast9o-FNl1hguYH_8ZohkC0eeB6U7NPna-S6l_eEaZvStvUKRPe8PMZse9va0fOzbkWoZtD_hwBLKmwCCeWYiXeHMAho6r4KazTz1s1hY3dLL_SaA1rtcO4_uvEGWHavzoo8GdwSLuekYbmN6O-xRsfqdp-oEQ11Sa8zw3xOmOd43TC0T_NSHA8H1QofX0tYkX8tRgSxFvgyin9eCwtJePrgTlJfo0vjOU0uHjL_mMZN3Gi2MY1AxPHxRYqaBWJ6vlKEZSRDQUQ_WzWdLPbmnLdz7pV_ZvWTO56mC-yNP2dN3oeDtlQLHcU8dz7Or8KYvsyV1QmNnofRaO7Nz1fK37y3ewbuqJ8JSN4V8NVULeY2LESOgJ_uXd0TEdnCuXqxbeDn2ohVoGx_arIfX5pUvGL2JKosKVtMo7MaEIfPvuE6k9Vyoo1wphxNR_EKjhuVPKtHVZo_RaxRQkyPvs8wt5vQu8FQxd4d6zw942vWS_k8nYORW2ubdL53KxDdy1kX-L_0S0)

### Class Diagram
![alt text](https://www.plantuml.com/plantuml/png/rLVDRjms4BxlK-ZMBM3NzqLXE6cCOe7M2EBs0MHfl6OXYIf9nJ2Cl7ibQfOVEI5JkUWbFYoa-VbcvflXYBwnhZPkwhlT3q_EZVPmTJLszR18VcfqVzLqjRMNhQfFfkvtEpKuCezrGzMVbimd6lNhhfh_7jNGBfBRmIdtylxbjgzLjwVbzr3TEwE6qu-72XPVEocoxr_kcoUjkzLaRwENGnMhPgZV9rS_TFIRikvdm5uNS3yQlUJFm2E_CJAKtuR-4fV_gz7JcDRlHOXvL_Uq7-OV4910mpnA6yB_oeT4_YARbiayY14PXU-dy8WEPecI5PMm8xlz2S-7bTGjyA8ixc1QLDBMC0AvhYIM2JApd0B7p9zRb8EDxKI-sheKSv4muh_G8uXP70KRl-Y-fq44O26QW8ZxCJmUAeX5800K3yFV4yLrtUbgeEUg9_UaMrkfVkneWQ0szVwaEuBNYTmYE9EH8nRw1SXlA3-OzsG-guPMd9jtazFFob1RJRog3c5fIGjFipV1mSwXU2MWYhOsZCrv379M8yOs367EpZ11Q-LPf9c2PstQsw7HBPaPBHLuU_sPZ54jLPsk_R5SJ_cmC3pnWrZM7yZLghFh54t3qfn5l6M_m-DTroqAkvyjilkQKS-ROHtwOpjRQxarfIyoEhPx1HUHoJIsjQEl32-gAHGP5nm3U7z3fpaucKNgkSfOoudqjNnl8tcAJpA2G-8zkAjUPVVV-lgmiPV97aM6mkxDdywwzGFzX_OQtw5ZwB4Sfr9S_kh5IMwiB7ofDtbY80Kjo9RF7UVARo9rhdkj-ErUwEfMrk0XHtqj7hE-UVBmEKNJYvzGwH3H9ar1iUZB1MSJgBgFNtCmBNKaOMGyCC5R75His_ZoqW8sYlSKwIBuWB4WXEN7uL57h71YWPQFoRk4YuXXUJaogD7MWPUtBxjnzcVY-6dJKiNzQjFt1VUVwyHPqWkxY8n5c3F2kVCMIoLW6Y_20osRXQ9VK9GXm3rV2OlqDrxDxBBj_zu2nfsx44Z8zq1UDIM0R0NCqswml6mAXc4AVY4EVY2P1MbppoXDObtsHfVqmr_KkEXEiRRed3bJOAoxmqPOfLsT4VAz399mGKo5JtgWkwb_81FBUKL6eNaxGEBxF9_pHzNHiQzF8Vo5rthk3Cs_le2cxZxCpoXKCB8M6nSS10-wVM5i29VHYUqu7nOXIlmPhDOWWwIg9TiODJgbXqIkuggumRqQ-Fn9cgAeyMciCcrqodK05ZM2N7Yior664HlwDUuJvY7sI3zrhr49qZxwNwgwlBp6KHOAxEgiIr7BcMHLN5PEX_z-f0BdPkU3PfE7HcQVTyghPVvI9-xBHGjkmuxhBL5GLfy1rbposHMLPsS-Xv41Ddj6nwA26bINNKFPskQZGY6Q18sCbi4ASNOtDBHJt_q3)
