/* * Copyright (C) 2025 Robert Moi, Goglotek LTD
 *  
 *  This file is part of the Fraud Detector System.
 *  
 *  The Fraud Detector System is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  The Fraud Detector System is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with the Fraud Detector System. If not, see <https://www.gnu.org/licenses/>.
 * */
export const config =
{
    BaseURL: "http://localhost/",
    OauthId: "user",
    OauthSecret: "password",
    TokeName: "snToken",
    UserDataName: "UserData",
    AdminIdentifier: "ADMIN",
    UserRoleName: "ROLE_USER",
    SuperAdminRoleName: "ROLE_SUPER_ADMIN",
    InvalidTokenResp: "invalid_token",
    //route urls
    FilesRouteURL: "/files",
    FileTransRouteURL: "/filetransactions",
    SignInRouteURL: "/signin",
    DashboardRouteURL: "/dashboard",
    DiscrepancyTransRouteURL: "/discrepancytransactions",
    MainTransRouteURL: "/maintransactions",
    FraudulentTransRouteURL: "/fraudulenttransactions",
    MissingTransRouteURL: "/missingtransactions",
    SignUpRouteURL: "/signup",
    UsersRouteURL: "/users",
    RolesRouteURL: "/roles",
    PaybillsRouteURL: "/paybills",
    //tab names
    FilesTabName: "Files",
    DashboardTabName: "Dashboard",
    FileTransTabName: "File Transactions",
    FraudulentTransTabName: "Fraudulent Transactions",
    MissingTransTabName: "Missing Transactions",
    MainTransTabName: "Main Transactions",
    UsersTabName: "Users",
    RolesTabName: "Roles",
    PaybillsTabName: "Paybills"
}