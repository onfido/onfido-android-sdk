# Onfido Android SDK Migration Guide

## `15.0.0` -> `15.1.0`

### String Changes

## Renamed

- `onfido_country_select_error_no_country_body_poa` -> `onfido_country_select_error_no_country_body`
- `onfido_nfc_sheet_scanning_button_secondary` -> `onfido_nfc_sheet_button_secondary`
- `onfido_nfc_intro_sheet_header_scanning` -> `onfido_nfc_intro_sheet_scanning_subtitle`
- `onfido_nfc_sheet_success_instruction` -> `onfido_nfc_sheet_success_instruction_passport`
- `onfido_doc_select_button_work_permit` -> `onfido_doc_select_button_permit_work`
- `onfido_doc_select_section_input_placeholder_country_copy` -> `onfido_doc_select_section_input_placeholder_country`
- `onfido_liveness_intro_loading_video` -> `onfido_video_intro_loader`
- `onfido_doc_select_section_input_placeholder_country` -> `onfido_doc_select_section_input_country_not_found`
- `onfido_doc_select_button_bill_detail_all` -> `onfido_doc_select_button_bill_detail`
- `onfido_liveness_fetch_challenge_error_description` -> `onfido_video_capture_prompt_network_timeout_detail`
- `onfido_liveness_intro_loading_video` -> `onfido_video_intro_loader`

## `14.0.0` -> `15.0.0`

### Breaking changes
- In order to use Studio/Workflow, now it is required to include it as a separate dependency as below: 
  `implementation "com.onfido.sdk:onfido-workflow:$onfidoSdkVersion"`

## `13.2.0` -> `14.0.0`

### Breaking changes
- Removed all references to wording around beta for the NFC feature. NFC is no longer beta and generally available.


## `12.3.1` -> `13.0.0`

### String Changes

#### Added

- `onfido_avc_face_alignment_feedback_move_left_accessibility`
- `onfido_avc_face_alignment_feedback_move_right_accessibility`
- `onfido_avc_face_alignment_feedback_move_up_accessibility`
- `onfido_avc_face_alignment_feedback_move_down_accessibility`

#### Removed

- `onfido_permission_recovery_button_secondary_cam`
- `onfido_permission_recovery_button_secondary_mic`
- `onfido_permission_recovery_button_secondary_both`
- `onfido_welcome_list_item_doc`
- `onfido_permission_recovery_subtitle_video`
- `onfido_permission_recovery_extra_instructions_cam`
- `onfido_permission_recovery_extra_instructions_mic`
- `onfido_permission_recovery_extra_instructions_both`
- `onfido_permission_recovery_body_mic`
- `onfido_permission_recovery_body_cam`
- `onfido_permission_recovery_body_both`
- `onfido_app_title_user_consent`
- `onfido_country_select_error_no_country_body`
- `onfido_welcome_list_item_video`
- `onfido_doc_confirmation_body_visa`
- `onfido_welcome_list_item_selfie`
- `onfido_welcome_list_header_photo`
- `onfido_welcome_list_header_record`
- `onfido_welcome_list_header_doc_video`
- `onfido_app_title_doc_video_confirmation`
- `onfido_doc_capture_header_license_front_auto`
- `onfido_doc_capture_header_visa_back`
- `onfido_doc_capture_header_passport_auto`
- `onfido_doc_capture_header_license_back_auto`
- `onfido_doc_capture_frame_accessibility_pp_cover_manual`
- `onfido_doc_capture_frame_accessibility_pp_manual`
- `onfido_welcome_list_item_face_generic`
- `onfido_doc_capture_alert_no_face_title`
- `onfido_doc_capture_alert_no_face_detail`
- `onfido_doc_capture_header_live_guidance_distance_ok`
- `onfido_doc_capture_header_live_guidance_distance_ok_accessibility`
- `onfido_nfc_intro_sheet_scanning_subtitle`
- `onfido_avc_connection_error_button_primary_reload`
- `onfido_doc_select_button_bill_detail`
- `onfido_poa_country_not_found`
- `onfido_doc_select_extra_no_mobile`
- `onfido_label_doc_type_driving_license_short`
- `onfido_label_doc_type_residence_permit_short`
- `onfido_face_tracking_timeout_button_retry`
- `onfido_allow`
- `onfido_permission_subtitle_video`
- `onfido_permission_body_video`
- `onfido_accessibility_liveness_confirmation_view`
- `onfido_nfc_sheet_ready_button_secondary`
- `onfido_nfc_intro_sheet_header_scan_retry`
- `onfido_nfc_sheet_scanning_instruction_retry`
- `onfido_nfc_intro_sheet_header_fail_passport`
- `onfido_nfc_intro_sheet_header_fail_card`
- `onfido_nfc_intro_sheet_header_ready_card`
- `onfido_nfc_intro_sheet_header_ready_passport`
- `onfido_poa_document_submission_empty`
- `onfido_flow_user_exit_message_user_cancelled`

## `12.2.3` -> `12.3.0`

### Added Strings

- `onfido_avc_intro_title_accessibility`
- `onfido_avc_intro_video_accessibility`
- `onfido_avc_face_capture_frame_accessibility`
- `onfido_avc_confirmation_left_side_complete_accessibility`
- `onfido_avc_confirmation_right_side_complete_accessibility`
- `onfido_avc_face_capture_title_accessibility`
- `onfido_avc_face_capture_recording_started_accessibility`
- `onfido_avc_face_alignment_feedback_face_aligned_accessibility`
- `onfido_avc_face_alignment_feedback_move_closer_accessibility`
- `onfido_avc_face_alignment_feedback_move_back_accessibility`
- `onfido_avc_face_alignment_title_accessibility`
- `onfido_avc_face_alignment_feedback_no_face_detected_accessibility`

## `11.5.0` -> `12.0.0`

### Breaking changes
- Removed the option to implement the user consent screen directly in your configuration during SDK initialization. It is now controlled by the Onfido backend. Please see our [Onfido privacy notices and consent migration guide](https://developers.onfido.com/guide/migration-guide-onfido-privacy-notices-and-consent) for further information.

### Changed strings:
- `onfido_doc_capture_header_live_guidance_doc_position_ok_accessibility`

## `11.0.0` -> `11.1.0`

### Added strings
- `onfido_nfc_intro_sheet_ready_subtitle_passport`
- `onfido_nfc_intro_sheet_scanning_subtitle`
- `onfido_nfc_intro_sheet_ready_subtitle_card`
- `onfido_nfc_intro_sheet_fail_instructions_card`
- `onfido_nfc_intro_sheet_fail_subtitle_card`
- `onfido_nfc_intro_sheet_fail_subtitle_passport`
- `onfido_nfc_intro_sheet_fail_instructions_passport`

## `10.3.1` -> `11.0.0`

### Breaking changes
- Removed deprecated `withToken(token)` and `withApplicant(ID)` from `OnfidoConfig.Builder` class. 
  Mobile tokens are not supported anymore, please use SDK tokens with `withSDKToken(token)`, 
  otherwise you will get an authorization exception when creating an applicant.

### Added strings
- `onfido_doc_capture_header_capturing`
- `onfido_nfc_intro_carousel_body_dont_move`
- `onfido_nfc_intro_carousel_body_last_page`
- `onfido_nfc_intro_carousel_body_lay_flat`
- `onfido_nfc_intro_carousel_body_phone_top`
- `onfido_nfc_intro_carousel_body_remove_cover`

### Changed strings:
- `onfido_nfc_intro_subtitle_passport`

### Removed strings:
- `onfido_doc_capture_header_scanning`

## `10.3.0` -> `10.3.1`

### Added strings
- `onfido_nfc_select_title_passport`
- `onfido_nfc_select_subtitle_passport`
- `onfido_nfc_select_body_passport`
- `onfido_nfc_select_button_primary_passport`
- `onfido_nfc_select_button_secondary_passport`
- `onfido_nfc_intro_title_passport`
- `onfido_nfc_intro_subtitle_passport`
- `onfido_nfc_intro_button_primary_passport`
- `onfido_nfc_intro_sheet_header_ready_passport`
- `onfido_nfc_fail_title_passport`
- `onfido_nfc_fail_list_item_remove_cover_passport`
- `onfido_nfc_fail_list_item_keep_contact_passport`
- `onfido_nfc_fail_button_primary_passport`
- `onfido_nfc_fail_button_secondary_passport`
- `onfido_nfc_select_title_card`
- `onfido_nfc_select_subtitle_card`
- `onfido_nfc_select_body_card`
- `onfido_nfc_select_button_primary_card`
- `onfido_nfc_select_button_secondary_card`
- `onfido_nfc_intro_title_card`
- `onfido_nfc_intro_subtitle_card`
- `onfido_nfc_intro_sheet_header_ready_card`
- `onfido_nfc_fail_title_card`
- `onfido_nfc_fail_list_item_remove_cover_card`
- `onfido_nfc_fail_list_item_keep_contact_card`
- `onfido_nfc_sheet_success_instruction_card`
- `onfido_nfc_intro_sheet_header_fail_passport`
- `onfido_nfc_intro_button_primary_card`
- `onfido_nfc_intro_sheet_header_fail_card`
- `onfido_nfc_fail_button_primary_card`
- `onfido_nfc_fail_button_secondary_card`

### Changed strings:
- `onfido_doc_capture_header_live_guidance_no_doc`
- `onfido_nfc_sheet_success_instruction`

### Removed strings:
- `onfido_nfc_select_title`
- `onfido_nfc_select_subtitle`
- `onfido_nfc_select_button_primary`
- `onfido_nfc_select_button_secondary`
- `onfido_nfc_select_body`
- `onfido_nfc_intro_title`
- `onfido_nfc_intro_sheet_header_ready`
- `onfido_nfc_sheet_ready_subtitle`
- `onfido_nfc_intro_subtitle`
- `onfido_nfc_sheet_success_intruction`
- `onfido_nfc_intro_button_primary`
- `onfido_nfc_fail_title`
- `onfido_nfc_fail_list_item_remove_covers`
- `onfido_nfc_fail_list_item_keep_contact`
- `onfido_nfc_fail_button_primary`
- `onfido_nfc_fail_button_secondary`

## `10.2.0` -> `10.3.0`

### Added strings
- `onfido_doc_capture_header_live_guidance_intro_doc_front_accessibility`
- `onfido_doc_capture_header_live_guidance_no_doc`
- `onfido_doc_capture_header_live_guidance_no_doc_accessibility`
- `onfido_doc_capture_header_live_guidance_distance_close`
- `onfido_doc_capture_header_live_guidance_distance_close_accessibility`
- `onfido_doc_capture_header_live_guidance_distance_far`
- `onfido_doc_capture_header_live_guidance_distance_far_accessibility`
- `onfido_doc_capture_header_live_guidance_distance_ok`
- `onfido_doc_capture_header_live_guidance_distance_ok_accessibility`
- `onfido_doc_capture_header_live_guidance_doc_too_left`
- `onfido_doc_capture_header_live_guidance_doc_too_left_accessibility`
- `onfido_doc_capture_header_live_guidance_doc_too_right`
- `onfido_doc_capture_header_live_guidance_doc_too_right_accessibility`
- `onfido_doc_capture_header_live_guidance_doc_too_high`
- `onfido_doc_capture_header_live_guidance_doc_too_high_accessibility`
- `onfido_doc_capture_header_live_guidance_doc_slightly_high`
- `onfido_doc_capture_header_live_guidance_doc_slightly_high_accessibility`
- `onfido_doc_capture_header_live_guidance_doc_too_low`
- `onfido_doc_capture_header_live_guidance_doc_too_low_accessibility`
- `onfido_doc_capture_header_live_guidance_doc_slightly_low`
- `onfido_doc_capture_header_live_guidance_doc_slightly_low_accessibility`
- `onfido_doc_capture_header_live_guidance_doc_position_ok`
- `onfido_doc_capture_header_live_guidance_doc_position_ok_accessibility`
- `onfido_doc_capture_header_live_guidance_intro_doc_front`
- `onfido_doc_capture_header_live_guidance_intro_doc_back`
- `onfido_doc_capture_header_live_guidance_intro_doc_back_accessibility`
- `onfido_doc_capture_header_live_guidance_intro_pp_photo`
- `onfido_doc_capture_header_live_guidance_intro_pp_photo_accessibility`

## `10.1.0` -> `10.2.0`

### Added strings:
- `onfido_welcome_list_header`
- `onfido_welcome_list_item_doc_photo`
- `onfido_welcome_list_item_doc_video`
- `onfido_welcome_list_item_doc_generic`
- `onfido_welcome_list_item_face_photo`
- `onfido_welcome_list_item_face_video`
- `onfido_welcome_list_item_face_generic`
- `onfido_nfc_select_title_passport`
- `onfido_nfc_select_subtitle_passport`
- `onfido_nfc_select_body_passport`
- `onfido_nfc_select_button_primary_passport`
- `onfido_nfc_select_button_secondary_passport`
- `onfido_nfc_intro_title_passport`
- `onfido_nfc_intro_subtitle_passport`
- `onfido_nfc_intro_button_primary_passport`
- `onfido_nfc_intro_sheet_header_ready_passport`
- `onfido_nfc_sheet_success_instruction`
- `onfido_nfc_fail_title_passport`
- `onfido_nfc_fail_list_item_remove_cover_passport`
- `onfido_nfc_fail_list_item_keep_contact_passport`
- `onfido_nfc_fail_button_primary_passport`
- `onfido_nfc_fail_button_secondary_passport`
- `onfido_nfc_select_title_card`
- `onfido_nfc_select_subtitle_card`
- `onfido_nfc_select_body_card`
- `onfido_nfc_select_button_primary_card`
- `onfido_nfc_select_button_secondary_card`
- `onfido_nfc_intro_title_card`
- `onfido_nfc_intro_subtitle_card`
- `onfido_nfc_intro_sheet_header_ready_card`
- `onfido_nfc_fail_title_card`
- `onfido_nfc_fail_list_item_remove_cover_card`
- `onfido_nfc_fail_list_item_keep_contact_card`
- `onfido_nfc_sheet_success_instruction_card`
- `onfido_nfc_intro_sheet_header_fail_passport`
- `onfido_nfc_intro_button_primary_card`
- `onfido_nfc_intro_sheet_header_fail_card`
- `onfido_nfc_fail_button_primary_card`
- `onfido_nfc_fail_button_secondary_card`

### Changed strings:
- `onfido_doc_capture_header_folded_doc_front`
- `onfido_doc_capture_header_folded_doc_back`
- `onfido_doc_capture_header_license_front`
- `onfido_doc_capture_header_license_back`
- `onfido_doc_capture_header_permit_front`
- `onfido_doc_capture_header_permit_back`
- `onfido_doc_capture_header_id_front`
- `onfido_doc_capture_header_id_back`
- `onfido_doc_capture_header_permit_work_front`
- `onfido_doc_capture_header_generic_front`
- `onfido_doc_capture_header_permit_work_back`
- `onfido_doc_capture_header_generic_back`
- `onfido_doc_capture_header_visa_front`
- `onfido_doc_capture_header_visa_back`
- `onfido_doc_capture_header_passport`
- `onfido_doc_capture_header_license_front_auto`
- `onfido_doc_capture_header_license_back_auto`
- `onfido_doc_capture_header_passport_auto`
- `onfido_doc_capture_frame_accessibility_pp_auto`
- `onfido_doc_capture_frame_accessibility_dl_front_auto`
- `onfido_doc_capture_frame_accessibility_dl_back_auto`
- `onfido_doc_capture_frame_accessibility_pp_manual`
- `onfido_doc_capture_frame_accessibility_dl_front_manual`
- `onfido_doc_capture_frame_accessibility_dl_back_manual`
- `onfido_doc_capture_frame_accessibility_ic_front_manual`
- `onfido_doc_capture_frame_accessibility_ic_back_manual`
- `onfido_doc_capture_frame_accessibility_rp_front_manual`
- `onfido_doc_capture_frame_accessibility_rp_back_manual`
- `onfido_doc_capture_frame_accessibility_dl_fr_front_manual`
- `onfido_doc_capture_frame_accessibility_dl_fr_back_manual`
- `onfido_doc_capture_frame_accessibility_ic_it_front_manual`
- `onfido_doc_capture_frame_accessibility_ic_it_back_manual`
- `onfido_doc_capture_frame_accessibility_ic_za_front_manual`
- `onfido_doc_capture_frame_accessibility_ic_za_back_manual`
- `onfido_doc_capture_frame_accessibility_pp_cover_manual`

### Removed strings:

- `onfido_nfc_select_title`
- `onfido_nfc_select_subtitle`
- `onfido_nfc_select_button_primary`
- `onfido_nfc_select_button_secondary`
- `onfido_nfc_select_body`
- `onfido_nfc_intro_title`
- `onfido_nfc_intro_sheet_header_ready`
- `onfido_nfc_intro_subtitle`
- `onfido_nfc_sheet_success_intruction`
- `onfido_nfc_intro_button_primary`
- `onfido_nfc_fail_title`
- `onfido_nfc_fail_list_item_remove_covers`
- `onfido_nfc_fail_list_item_keep_contact`
- `onfido_nfc_fail_button_primary`
- `onfido_nfc_fail_button_secondary`

## `10.0.0` -> `10.1.0`

### Added strings:
- `onfido_doc_capture_frame_accessibility_pp_auto`
- `onfido_doc_capture_frame_accessibility_dl_front_auto`
- `onfido_doc_capture_frame_accessibility_dl_back_auto`
- `onfido_doc_capture_frame_success_accessibility`
- `onfido_doc_capture_frame_accessibility_rp_front_manual`
- `onfido_doc_capture_frame_accessibility_rp_back_manual`
- `onfido_doc_capture_frame_accessibility_ic_front_manual`
- `onfido_doc_capture_frame_accessibility_ic_back_manual`
- `onfido_doc_capture_frame_accessibility_dl_fr_front_manual`
- `onfido_doc_capture_frame_accessibility_dl_fr_back_manual`
- `onfido_doc_capture_frame_accessibility_ic_it_front_manual`
- `onfido_doc_capture_frame_accessibility_ic_it_back_manual`
- `onfido_doc_capture_frame_accessibility_ic_za_front_manual`
- `onfido_doc_capture_frame_accessibility_ic_za_back_manual`
- `onfido_video_capture_frame_accessibility`

### Changed strings:
- `onfido_selfie_capture_frame_accessibility`

## `9.3.1` -> `10.0.0`

### Breaking changes
- Removed deprecated methods and constructors of DocumentType class

### Added strings:
- `onfido_permission_recovery_button_primary`
- `onfido_permission_recovery_list_item_how_to_cam`
- `onfido_permission_recovery_list_item_action_cam`
- `onfido_permission_recovery_list_item_how_to_mic`
- `onfido_permission_recovery_list_item_action_mic`
- `onfido_permission_recovery_list_item_how_to_both`
- `onfido_permission_recovery_list_item_action_both`
- `onfido_video_capture_header_challenge_turn_forward`
- `onfido_generic_uploading`
- `onfido_doc_confirmation_alert_crop_detail`
- `onfido_doc_confirmation_alert_crop_title`
- `onfido_doc_upload_progress_label`
- `onfido_video_intro_list_item_time_limit_copy`
- `onfido_app_title_selfie_confirmation`
- `onfido_app_title_video_confirmation`
- `onfido_doc_capture_header_passport_auto`
- `onfido_doc_capture_detail_passport`
- `onfido_doc_capture_header_license_front_auto`
- `onfido_video_capture_body`
- `onfido_doc_capture_header_license_back_auto`
- `onfido_welcome_list_header_photo`
- `onfido_welcome_list_header_record`
- `onfido_app_title_doc_confirmation`
- `onfido_app_title_permission`
- `onfido_app_title_selfie_intro`
- `onfido_app_title_video_intro`
- `onfido_welcome_list_header_doc_video`
- `onfido_app_title_doc_video_confirmation`
- `onfido_video_capture_turn_success_accessibility`

### Removed strings:
- `onfido_permission_extra_instructions_cam`
- `onfido_permission_extra_instructions_mic`
- `onfido_permission_extra_instructions_both`
- `onfido_permission_recovery_button_primary_cam`
- `onfido_permission_recovery_button_primary_mic`
- `onfido_permission_recovery_button_primary_both`
- `onfido_app_title_doc_capture_visa`
- `onfido_app_title_doc_capture_permit_work`
- `onfido_app_title_doc_capture_generic`
- `onfido_app_title_doc_capture_passport`
- `onfido_app_title_doc_capture_permit`
- `onfido_app_title_doc_capture_id`
- `onfido_app_title_doc_capture_license`
- `onfido_doc_capture_detail_passport_auto`
- `onfido_doc_confirmation_body_passport`
- `onfido_doc_confirmation_body_permit`
- `onfido_doc_confirmation_body_license`
- `onfido_doc_confirmation_body_id`
- `onfido_doc_confirmation_body_permit_work`
- `onfido_selfie_confirmation_button_primary_long`
- `onfido_selfie_confirmation_button_primary_short`
- `onfido_selfie_confirmation_button_secondary_long`
- `onfido_selfie_confirmation_button_secondary_short`
- `onfido_video_confirmation_title`
- `onfido_welcome_list_item_doc_video`
- `onfido_doc_confirmation_alert_no_barcode_title`
- `onfido_welcome_button_primary_selfie`
- `onfido_welcome_button_primary_video`
- `onfido_doc_capture_detail_license_front_auto`

### Changed Strings:
- `onfido_app_title_welcome`
- `onfido_permission_subtitle_cam`
- `onfido_permission_body_cam`
- `onfido_permission_recovery_title_cam`
- `onfido_permission_recovery_subtitle_cam`
- `onfido_permission_subtitle_mic`
- `onfido_permission_body_mic`
- `onfido_permission_recovery_title_mic`
- `onfido_permission_recovery_subtitle_mic`
- `onfido_permission_subtitle_both`
- `onfido_permission_body_both`
- `onfido_permission_button_primary_both`
- `onfido_permission_recovery_title_both`
- `onfido_permission_recovery_subtitle_both`
- `onfido_doc_confirmation_button_primary_passport`
- `onfido_doc_confirmation_button_primary_permit`
- `onfido_doc_confirmation_button_primary_license`
- `onfido_doc_confirmation_button_primary_id`
- `onfido_doc_confirmation_button_primary_visa`
- `onfido_doc_confirmation_button_primary_generic`
- `onfido_doc_confirmation_button_primary_barcode`
- `onfido_doc_confirmation_button_primary_permit_work`
- `onfido_doc_confirmation_button_secondary_passport`
- `onfido_doc_confirmation_button_secondary_id`
- `onfido_doc_confirmation_button_secondary_license`
- `onfido_doc_confirmation_button_secondary_permit`
- `onfido_doc_confirmation_button_secondary_permit_work`
- `onfido_doc_confirmation_button_secondary_visa`
- `onfido_doc_confirmation_button_secondary_generic`
- `onfido_video_capture_header_challenge_turn_left`
- `onfido_video_capture_header_challenge_turn_right`
- `onfido_video_intro_button_primary`
- `onfido_selfie_intro_button_primary`
- `onfido_doc_select_button_license`
- `onfido_doc_select_button_permit`
- `onfido_welcome_subtitle`
- `onfido_welcome_list_item_doc`
- `onfido_welcome_list_item_selfie`
- `onfido_welcome_list_item_video`
- `onfido_video_intro_title`
- `onfido_doc_select_title`
- `onfido_doc_select_subtitle`
- `onfido_country_select_bottom_sheet_details`
- `onfido_country_select_bottom_sheet_link_doc_select`
- `onfido_selfie_intro_subtitle`
- `onfido_selfie_intro_list_item_face_forward`
- `onfido_selfie_intro_list_item_no_glasses`
- `onfido_selfie_capture_body`
- `onfido_selfie_confirmation_body`
- `onfido_doc_capture_detail_visa_front`
- `onfido_doc_capture_detail_visa_back`
- `onfido_selfie_confirmation_alert_no_face_title`
- `onfido_selfie_confirmation_alert_no_face_detail`
- `onfido_doc_confirmation_alert_no_doc_detail`
- `onfido_doc_confirmation_body_visa`
- `onfido_doc_confirmation_body_generic`
- `onfido_doc_capture_detail_permit_front`
- `onfido_doc_capture_detail_permit_back`
- `onfido_doc_capture_detail_license_front`
- `onfido_doc_capture_detail_license_back`
- `onfido_doc_capture_detail_id_front`
- `onfido_doc_capture_detail_id_back`
- `onfido_doc_capture_detail_permit_work_front`
- `onfido_doc_capture_detail_generic_front`
- `onfido_doc_capture_detail_permit_work_back`
- `onfido_doc_capture_detail_generic_back`
- `onfido_doc_capture_alert_glare_title`
- `onfido_video_intro_subtitle`
- `onfido_video_intro_list_item_move_speak`
- `onfido_video_intro_list_item_time_limit`
- `onfido_video_capture_prompt_detail_timeout`
- `onfido_video_confirmation_button_audio_unmute`
- `onfido_country_select_error_no_country_title`
- `onfido_country_select_error_no_country_body`
- `onfido_doc_capture_alert_manual_capture_detail`
- `onfido_doc_confirmation_alert_blur_title`
- `onfido_doc_confirmation_alert_blur_detail`
- `onfido_doc_capture_alert_no_barcode_title`
- `onfido_doc_capture_header_passport`
- `onfido_permission_body_mic`
- `onfido_permission_body_both`
- `onfido_doc_confirmation_button_primary_passport`
- `onfido_doc_confirmation_button_primary_id`
- `onfido_doc_confirmation_button_primary_visa`
- `onfido_doc_confirmation_button_primary_generic`
- `onfido_doc_confirmation_button_primary_barcode`
- `onfido_doc_confirmation_button_primary_folded_doc`
- `onfido_selfie_confirmation_button_secondary`
- `onfido_selfie_confirmation_button_primary`
- `onfido_app_title_doc_select`
- `onfido_welcome_button_primary_doc`
- `onfido_video_intro_video_accessibility`
- `onfido_video_capture_frame_success_accessibility`
- `onfido_video_confirmation_video_accessibility`
- `onfido_video_capture_header_challenge_digit_instructions_accessibility`
- `onfido_video_capture_header_challenge_turn_instructions_accessibility`
- `onfido_video_capture_header_extra_instructions_accessibility`
- `onfido_video_capture_header_challenge_turn_left_accessibility`
- `onfido_video_capture_header_challenge_turn_right_accessibility`
- `onfido_video_confirmation_button_play_and_pause_accessibility`
- `onfido_doc_capture_button_accessibility`

## `9.2.0` -> `9.3.0`

### Changes
- Migrated from JCenter to Maven Central. You will need to add `mavenCentral()` into the `repositories` block, if it is not already added.

## `9.0.0` -> `9.1.0`

### Added Strings:
- `onfido_user_consent_button_primary`
- `onfido_user_consent_button_secondary`
- `onfido_user_consent_prompt_no_consent_title`
- `onfido_user_consent_prompt_no_consent_detail`
- `onfido_user_consent_prompt_button_primary`
- `onfido_user_consent_prompt_button_secondary`
- `onfido_app_title_user_consent`

#### Deprecation
- `CAMERA_PERMISSION_DENIED` in `ExitCode` enum is deprecated as permissions are being handled inside the SDK.

## `8.1.0` -> `9.0.0`

### Breaking changes
- Updated to [OkHttp4](https://square.github.io/okhttp/upgrading_to_okhttp_4/). In order to prevent runtime issues due to library conflicts, the host app must match the major version of the OkHttp.

## `7.4.0` -> `8.0.0`

### Breaking changes
- Minimum Android API level (minSdkVersion) support has been updated from 16 to 21. Onfido SDK will stop supporting Android 4.x starting with this version.

### Added Strings:
- `onfido_outro_body`

### Changed Strings:
- `onfido_video_confirmation_button_primary`

## `7.3.0` -> `7.4.0`

### Added Strings:
- `onfido_app_title_doc_capture_id_za`

### Changed Strings:
- ⚠️ Most of the localisation keys have been renamed. If you have customised any of the Onfido SDK's strings in your project, you may use [migrate-keys.rb](key_migration/migrate-keys.rb) script and key mapping file [key_migration_7_3_0_mapping.json](key_migration/key_migration_7_3_0_mapping.json) to migrate from 7.3.0 to 7.4.0

```bash
  migrate-keys.rb --files-path <app/src/main/res/> --platform android --key-mapping-file key_migration_7_3_0_mapping.json
```

## `7.0.0` -> `7.1.0`

#### Deprecation
- EnterpriseFeatures class' constructor is deprecated. Please use `EnterpriseFeatures.Builder` instead.

#####before

###### Kotlin
```kotlin
EnterpriseFeatures(true)
```
###### Java
```java
new EnterpriseFeatures(true);
```

#####after

###### Kotlin
```kotlin
val enterpriseFeatures: EnterpriseFeatures = EnterpriseFeatures.Builder().withHideOnfidoLogo(true).build()
```
###### Java
```java
EnterpriseFeatures enterpriseFeatures = EnterpriseFeatures.builder().withHideOnfidoLogo(true).build();
```

## `6.0.0` -> `7.0.0`

### Breaking changes
- Migrated to AndroidX. If your app hasn't completed AndroidX migration yet, please see [AndroidX Migration](https://developer.android.com/jetpack/androidx/migrate).

### Changed Strings:
- `onfido_autocapture_manual_fallback_title`
- `onfido_autocapture_manual_fallback_description`

## `5.6.0` -> `6.0.0`

### Breaking changes
- Removed out-of-the-box Portuguese (`pt`) translation. If you would like to keep supporting Portuguese by providing your own XML files, please see [README](https://github.com/onfido/onfido-android-sdk#4-localisation)
- SDK will return `DocumentType.UNKNOWN` to mirror the Onfido API response as part of the `Captures` object which is provided by `handleActivityResult` if `residence permit` or `generic` is selected

### Added strings:
- `onfido_accessibility_video_pause`
- `onfido_accessibility_video_play`

## `5.5.0` -> `5.6.0`

#### Deprecation
- The `CaptureScreenStep` class is deprecated. We now recommend `DocumentCaptureStepBuilder` to customise document capture steps

#####before

###### Java

```java
new CaptureScreenStep(DocumentType.NATIONAL_IDENTITY_CARD, CountryCode.GB);
```

###### Kotlin

```kotlin
CaptureScreenStep(DocumentType.NATIONAL_IDENTITY_CARD, CountryCode.GB)
```

#####after

###### Java

```java
DocumentCaptureStepBuilder.forNationalIdentity()
                .withCountry(CountryCode.GB)
                .build();
```

###### Kotlin

```kotlin
DocumentCaptureStepBuilder.forNationalIdentity()
                .withCountry(CountryCode.GB)
                .build()
```

- The `FaceCaptureStep` class is deprecated. We now recommend `FaceCaptureStepBuilder` to customise face capture steps

#####before

###### Java

```java
FlowStep selfieCaptureStep = new FaceCaptureStep(new FaceCaptureVariantPhoto(false));

FlowStep videoCaptureStep = new FaceCaptureStep(new FaceCaptureVariantVideo(false));
```

###### Kotlin

```kotlin
val selfieCaptureStep = FaceCaptureStep(FaceCaptureVariantPhoto(false))

val videoCaptureStep = FaceCaptureStep(FaceCaptureVariantVideo(false))
```

#####after

###### Java

```java
FlowStep selfieCaptureStep = FaceCaptureStepBuilder.forPhoto()
                .withIntro(false)
                .build();

FlowStep videoCaptureStep = FaceCaptureStepBuilder.forVideo()
                .withIntro(false)
                .build();
```

###### Kotlin

```kotlin
val selfieCaptureStep = FaceCaptureStepBuilder.forPhoto()
                .withIntro(false)
                .build()

        val videoCaptureStep = FaceCaptureStepBuilder.forVideo()
                .withIntro(false)
                .build()
```

### Added strings:
- `onfido_mrz_not_detected_title`
- `onfido_mrz_not_detected_subtitle`

## `5.3.3` -> `5.4.0`

### Added strings:
- `onfido_italian_id_capture_title`
- `onfido_french_driving_license_capture_title`
- `onfido_folded_paper_option`
- `onfido_plastic_card_option`
- `onfido_driving_license_type_selection_title`
- `onfido_national_identity_type_selection_title`
- `onfido_folded_paper_front_capture_title`
- `onfido_folded_paper_front_capture_subtitle`
- `onfido_folded_paper_back_capture_title`
- `onfido_folded_paper_back_capture_subtitle`
- `onfido_folded_paper_confirmation_title`
- `onfido_upload_photo`
- `onfido_retake_photo`

#### Deprecation
- Deprecated properties of `DocumentType` class

## `4.5.0-F5` -> `5.3.2` - [enterprise]

### Breaking changes
- Removed `OnfidoCertificatePinningSettings` class which has `ONFIDO_API` parameter to provide root certificate's hash value.
For more information, please visit our [README.md](README.md#certificate-pinning) 

## `5.2.0` -> `5.3.0`

### Added strings:
- `onfido_label_doc_type_generic_up`

### Changed Strings:
- `onfido_accessibility_liveness_video_example`

## `5.1.0` -> `5.2.0`

### Added strings:
- `onfido_accessibility_liveness_face_detected`

## `5.0.1` -> `5.1.0`

### Added strings:
- `onfido_accessibility_camera_document_capture_view`
- `onfido_accessibility_face_confirmation_view`
- `onfido_accessibility_document_confirmation_view`
- `onfido_accessibility_liveness_confirmation_view`
- `onfido_accessibility_video_preview_recorded`
- `onfido_accessibility_liveness_digits`
- `onfido_accessibility_liveness_move`
- `onfido_accessibility_then`
- `onfido_accessibility_liveness_left`
- `onfido_accessibility_liveness_right`
- `onfido_accessibility_liveness_play_pause`
- `onfido_accessibility_take_picture`

## `5.0.0` -> `5.0.1`

### Changed
- No longer obfuscating `com.monadtek.mvp` package

## `4.11.0` -> `5.0.0`

### Applicant removal and Token change

#### Breaking change 
- Applicant class removed from the SDK
- Applicant parameter removed from the `OnfidoResultListener` callback methods
- Removed deprecated `withApplicant(Applicant)` method on the `OnfidoConfig.Builder` class as SDK no longer creates applicants

#### Deprecation
- The `withApplicant(String)` and  the `withToken(String)` methods are deprecated  from the `OnfidoConfig.Builder`, we now recommend that create a [SDK Token](README.md#41-sdk-token) on your backend which contains `applicantId` and use `withSDKToken(String)` method to initialise the OnfidoConfig 



##### Before

The initialisation of the SDK by passing the applicant ID and the (static) mobile token:
```
val OnfidoConfig config = OnfidoConfig.builder()
            .withToken("YOUR_MOBILE_TOKEN")
            .withApplicant("YOUR_APPLICANT_ID")
            .build();
```

or if you were using the deprecated `withApplicant(Applicant)` method:
```
val applicant = Applicant.builder()  
        .withFirstName("Your first name")  
        .withLastName("Your first name")  
        .build()  
  
val onfidoConfig = OnfidoConfig.builder()  
        .withApplicant(applicant)
        .withToken("YOUR_MOBILE_TOKEN")
        .build();
```

The SDK callback where the `Applicant` type object was passed:
```
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    ...
    onfido.handleActivityResult(resultCode, data, new Onfido.OnfidoResultListener() {
        @Override
        public void userCompleted(Applicant applicant, Captures captures) {
            //communicate with your backend and initiate the check
        }

        @Override
        public void userExited(ExitCode exitCode, Applicant applicant) {
            //User left the sdk flow without completing it
        }

        @Override
        public void onError(OnfidoException exception, @Nullable Applicant applicant) {
            // An exception occurred during the flow
        }
    });
}
```

#####  After

Neither the (static) mobile token nor the Applicant ID are expected anymore, you are now expected to pass the SDK token which is generated by calling the Onfido API:
```
val sdkToken: String = createSdkToken() // https://github.com/onfido/onfido-android-sdk/blob/master/README.md#41-sdk-token 
  
val onfidoConfig = OnfidoConfig.builder(context)  
        .withSDKToken(sdkToken) 
        .build()
```

The SDK callback no longer passes an `Applicant` object:
```
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    ...
    onfido.handleActivityResult(resultCode, data, new Onfido.OnfidoResultListener() {
        @Override
        public void userCompleted(Captures captures) {
            //communicate with your backend and initiate the check
        }

        @Override
        public void userExited(ExitCode exitCode) {
            //User left the sdk flow without completing it
        }

        @Override
        public void onError(OnfidoException exception) {
            // An exception occurred during the flow
        }
    });
}
```

### US Driver's license optional feature

#### Breaking change
- Removed `withUSDLAutocapture()` method from the `OnfidoConfig.Builder`. Autocapture enabled by default for the US driving licences. 


### Added strings:
- `onfido_autocapture_manual_fallback_title`
- `onfido_autocapture_manual_fallback_description`

### Removed strings:
- `onfido_autocapture_info`
- `onfido_press_button_capture`
- `onfido_barcode_error_subtitle`
- `onfido_barcode_error_third_title`

### Changed Strings:
- `onfido_barcode_error_title`

## `4.8.0` -> `4.11.0`

### Added strings:
- `onfido_accessibility_liveness_video_example`
- `onfido_accessibility_camera_face_capture_view`


## `4.7.0` -> `4.8.0`

### Added strings:
- `onfido_label_doc_type_work_permit_up`
- `onfido_message_side_document_front_generic`
- `onfido_message_side_document_back_generic`
- `onfido_message_check_readability_subtitle_generic`
- `onfido_message_document_capture_info_front_generic`
- `onfido_message_document_capture_info_back_generic`
- `onfido_confirm_generic_document`

### Removed strings:
- `onfido_label_doc_type_passport`
- `onfido_label_doc_type_driving_license`
- `onfido_label_doc_type_id_card`
- `onfido_label_doc_type_visa`

## `4.6.0` -> `4.7.0`

### Changed Strings:
- `onfido_country_selection_toolbar_title`
- `onfido_unsupported_document_description`

## `4.5.1` -> `4.6.0`

### Added Strings:
- `onfido_label_doc_type_visa`
- `onfido_label_doc_type_visa_up`
- `onfido_message_document_visa`
- `onfido_message_check_readability_subtitle_visa`
- `onfido_confirm_visa`
- `onfido_liveness_intro_subtitle`
- `onfido_liveness_intro_step_1_title`
- `onfido_liveness_intro_step_2_title`
- `onfido_liveness_intro_loading_video`
- `onfido_reload`
- `onfido_unable_load_unstable_network`
- `onfido_unable_load_offline`

### Removed strings:
- `onfido_next`
- `onfido_liveness_intro_title`
- `onfido_liveness_intro_subtitle_1_action`
- `onfido_liveness_intro_subtitle_2_actions`
- `onfido_liveness_intro_subtitle_some_actions`
- `onfido_liveness_intro_third_subtitle_1_action`
- `onfido_liveness_intro_third_subtitle_2_actions`
- `onfido_liveness_intro_third_subtitle_some_actions`
- `onfido_liveness_challenge_open_mouth_title`
- `onfido_liveness_challenge_next`
- `onfido_liveness_challenge_stop`
- `onfido_stop`
- `onfido_liveness_challenge_recording`
- `onfido_video_recorded`
- `onfido_camera_access_recover_instructions_subtitle`

## `3.0.0` -> `4.0.0`
- Changed the `Applicant` parameter on the `userCompleted(Applicant applicant, Captures captures)` callback to be a non-nullable field,
meaning that we guarantee this field will always contain information about the applicant whenever this callback is called. Any null check being applied may now be deleted.
- Changed the `Applicant` parameter on the `onError(OnfidoException exception, @Nullable Applicant applicant)` callback to be a nullable value,
meaning that depending on the error originating the callback, the applicant details might be `null`. Therefore, developers should add the correspondent null check before accessing its information.

## `2.4.0` -> `3.0.0`
- Added `onError(OnfidoException exception, Applicant applicant)` method on the `Onfido` object, 
used to get the result of the identity verification flow. This callback will be called whenever an exception that the end-user 
should not be able to overcome by itself occurs during the flow.
The new method should be implemented and the exception handled accordingly.

- Upgraded our infrastructure and SDK client SSL configurations to support TLSv1.2 only. According to the relevant [Google documentation](https://developer.android.com/reference/javax/net/ssl/SSLSocket.html), this support comes enabled by default on every device running 
Android API 20+. In case you need to support devices older than that in your integration with the Onfido Android SDK, we need to access Google Play Services to install the latest security updates, which enable this support.
  As such, if you don't use Google Play Services on your integration yet, we require you to add the following dependency:
  
  ```gradle
  compile ('com.google.android.gms:play-services-base:x.y.z') {
             exclude group: 'com.android.support' // to avoid conflicts with your current support library
  }
  ```

## `2.0.0` -> `2.1.0`

### Deprecations
- Deprecated `withApplicant(Applicant applicant)` method. We now recommend that you create an Onfido applicant yourself on your backend and 
the `withApplicant(String id)` method should be called with the id of the created applicant.

## `1.0.0` -> `2.0.0`

### Breaking changes
- Removed `FlowStep.MESSAGE_FACE_VERIFICATION`, which is now automatically added before any face capture with the variant `FaceCaptureVariant.PHOTO`.
This way, any inclusion of this step in a custom flow should be removed

## `0.9.2` -> `1.0.0`

### Breaking changes

- Removed the `allowMetrics(boolean)` method from the `OnfidoConfig.Builder` object. Every call to this method should be deleted
- Removed the previously deprecated `FlowStep.MESSAGE_IDENTIFY_VERIFICATION` enum instance, as it was too specific for our generic flow intentions. 
Every previous inclusion of this object on a flow should be replaced by a custom `MessageScreenStep`
