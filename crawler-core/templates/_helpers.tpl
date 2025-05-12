{{- define "crawler-core.name" -}}
{{ .Chart.Name }}
{{- end }}

{{- define "crawler-core.fullname" -}}
{{ .Release.Name }}-{{ include "crawler-core.name" . }}
{{- end }}
