FROM node:16-alpine AS build
 ENV NG_BUILD_MAX_WORKERS=2 NODE_OPTIONS="--max-old-space-size=1024"
WORKDIR /app
 COPY package*.json ./
 RUN --mount=type=cache,id=course7-npm,target=/root/.npm,sharing=locked npm ci --no-audit --no-fund --maxsockets=8 --fetch-retries=5 --fetch-timeout=300000
 COPY . .
 RUN --mount=type=cache,id=course7-angular14,target=/app/.angular/cache,sharing=locked npm run build
 FROM nginx:1.27-alpine
 COPY --from=build /app/dist/smart-home /usr/share/nginx/html
 COPY nginx.conf /etc/nginx/conf.d/default.conf
 EXPOSE 80
