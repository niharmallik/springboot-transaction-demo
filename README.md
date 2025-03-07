# springboot-transaction-demo

https://www.linkedin.com/in/nihar-mallik/

Developer Handover Document: Post Payment Validation Scenarios and Error Handling

Introduction

This document serves as a comprehensive handover guide for developers handling post-payment validation scenarios in the Pismo platform. It details expected API responses, error codes, and required handling measures for various transaction cases.

1. Successful Transaction

201 - Created

The payment transaction has been successfully processed and recorded.

2. Client-Side Errors (400 Series)

Account and Transaction Validations

400 - Operations blocked for account: The account has restrictions preventing the transaction.

400 - Corporate account not found: The provided corporate account does not exist.

400 - Unrecognized account ID type: The account ID type is not recognized by the system.

400 - Invalid account status: The account status does not allow transactions.

400 - Invalid account program type: The provided account does not match the expected program type.

400 - Target account cannot be credited: The target account does not accept credits.

400 - Sender account cannot be debited: The sender account does not allow debits.

Amount and Currency Validations

400 - Amount value less than zero: The transaction amount cannot be negative.

400 - Amount value exceed: The amount surpasses the permissible transaction limit.

400 - Amount value required: The transaction amount must be specified.

400 - Invalid amount: The amount format is incorrect.

400 - Currency required: The currency field is missing.

400 - Invalid currency: The specified currency is invalid.

400 - Currency mismatch: The currency does not match the account’s configured currency.

Processing and Tracking Validations

400 - Processing code exceed size: The processing code exceeds the allowed character limit.

400 - External Account ID exceed size: The External Account ID is too long.

400 - External Account ID required: This field must be provided.

400 - Tracking ID exceed size: The tracking ID is too long.

400 - External account ID contains character not permitted: The External Account ID includes invalid characters.

400 - Tracking ID required: A tracking ID must be provided.

400 - Tracking ID already in use: The provided tracking ID is already associated with another transaction.

400 - Tracking ID already in use by another account: The same tracking ID is being used for a different account.

Validation Rules & Force Posting Restrictions

400 - Validation rules with force post not allowed: Force posting is not permitted with validation rules.

400 - Invalid validation rule: The specified validation rule is incorrect.

400 - Invalid params for validation rule: One or more parameters provided for validation are incorrect.

400 - Force operation not allowed: The requested operation cannot be forced.

Business Date and Earmark Validations

400 - Invalid payment date: The payment date is in an incorrect format or outside allowed limits.

400 - Payment business date before earmark creation: The payment date is earlier than the earmark creation date.

400 - Future dated earmark payments are not allowed: Payments cannot be scheduled for future dates if earmarks are involved.

400 - Future dated payments only support default debit processing code: Custom debit processing codes are not allowed for future payments.

400 - Future dated payments only support default credit processing code: Custom credit processing codes are not allowed for future payments.

400 - Back business dating disabled: Transactions cannot be backdated.

400 - Invalid business date for current business day cycle: The provided business date is not valid for the ongoing business day.

400 - Invalid business date format: The provided date format does not match the required standard.

400 - Backdating not permitted: Transactions cannot be processed for past dates.

400 - Holiday error: Transactions cannot be processed on holidays.

400 - Weekend error: Transactions cannot be processed on weekends.

Earmark Validations

400 - Invalid debit earmark processing code: The earmark processing code provided for debit is invalid.

400 - Earmark not active: The earmark is inactive and cannot be used for the transaction.

400 - Earmark insufficient balance: The earmark balance is not enough to process the transaction.

404 - Earmark not found: The earmark ID does not exist.

Operation & Transfer Rules

400 - Orgs operation not found or parameterized: The requested operation is not configured for the organization.

400 - Operation not allowed by reason: The transaction is blocked due to a specific reason (e.g., policy restriction).

400 - Debit not permitted: Debit transactions are not allowed for this account.

400 - Credit not permitted: Credit transactions are not allowed for this account.

400 - Transfer must be sent by debit account: The transfer operation requires the sender to be a debit account.

Metadata Validation

400 - Corporate Metadata must be an object: Metadata provided for the transaction must be structured as an object.

400 - Misconfigured administrative division: The administrative division settings are incorrect, blocking the transaction.

3. Unauthorized Access (401 Series)

401 - Unauthorized account: The request is not authorized for the given account.

4. Conflict Errors (409 Series)

409 - Invalid time zone: The time zone used for processing is incorrect.

409 - Tracking ID already in use: The tracking ID is already associated with another transaction.


----------------------------
Subject: Thank You & Farewell

Dear [Team/Manager's Name],

As my time at [Company Name] comes to an end, I want to express my sincere gratitude for the incredible journey. Working with such a talented team has been a privilege, and I truly appreciate the support, collaboration, and friendships formed along the way.

A special thanks to [mention specific colleagues or managers] for their guidance and encouragement. Your mentorship has been invaluable.

Though I’m moving on, I’d love to stay in touch. You can reach me at [LinkedIn/personal email]. Wishing you all continued success!

Best regards,[Your Name][Your Contact Information][LinkedIn (if applicable)]

409 - Tracking ID already in use by another account: The same tracking ID is being used for a different account.

423 - Transaction using this tracking ID in progress: A transaction is already being processed with this tracking ID.

5. Server-Side Errors (500 Series)

500 - Generic internal error: An unspecified error occurred on the server-side.

This document ensures that developers can correctly handle API responses, implement necessary error handling, and troubleshoot payment validation issues effectively.
